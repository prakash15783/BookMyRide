package bookmyride.scheduler.uber;

import java.sql.Timestamp

import bookmyride.BMRAuthService
import bookmyride.BookMyRideConstants
import bookmyride.CommonDataStore
import bookmyride.MailQueue
import bookmyride.RequestStatus
import bookmyride.RideRequest
import bookmyride.RideRequestLog
import bookmyride.RideResponse
import bookmyride.User
import bookmyride.retry.BackOffUtil;
import bookmyride.scheduler.AbstractRideRequestProcessor

import com.uber.sdk.rides.client.Callback
import com.uber.sdk.rides.client.Response
import com.uber.sdk.rides.client.UberRidesAsyncService
import com.uber.sdk.rides.client.UberRidesService
import com.uber.sdk.rides.client.UberRidesSyncService
import com.uber.sdk.rides.client.error.ApiException
import com.uber.sdk.rides.client.error.NetworkException
import com.uber.sdk.rides.client.error.SurgeError
import com.uber.sdk.rides.client.error.UberError
import com.uber.sdk.rides.client.model.Location
import com.uber.sdk.rides.client.model.Ride
import com.uber.sdk.rides.client.model.RideRequestParameters
import com.uber.sdk.rides.client.model.RideRequestParameters.Builder

public class UberRideRequestProcessor extends AbstractRideRequestProcessor {
	private static final MailQueue mailQueue = (MailQueue)CommonDataStore.getDataStore(BookMyRideConstants.MAIL_QUEUE);
	
	@Override
    public boolean processRideRequest(RideRequest rideRequest) {
        
    	if(rideRequest != null ){
			try {
				//create log object
				RideRequestLog rideReqLog = createRequestLog(rideRequest);
				
				//User uber sdk to process ride
				UberRidesService uberRidesService = getUberSyncService(rideRequest.getRequester());//getUberRidesService(rideRequest.getRequester(),"sync");
				RideRequestParameters rideRequestParameters = getRideRequestParameters(rideRequest);
				if(rideRequestParameters != null){
					requestRide(uberRidesService,rideRequestParameters,rideRequest,rideReqLog)
				}
			}   
			catch (Exception e) {
				System.out.println("Something went wrong !!");
				e.printStackTrace(); 
			}
					
    	}
        return true;
    }

	private void requestRide(UberRidesService uberRidesService,RideRequestParameters rideRequestParameters
		,RideRequest rideRequest, RideRequestLog rideReqLog ){
		if(uberRidesService instanceof UberRidesAsyncService){
			((UberRidesAsyncService)uberRidesService).requestRide(rideRequestParameters,  new RideCallback(rideRequest,rideReqLog));
		}else if(uberRidesService instanceof UberRidesSyncService){
			Response<Ride> rideResponse = ((UberRidesSyncService)uberRidesService).requestRide(rideRequestParameters);
			
			RideCallback rideCallback = new RideCallback(rideRequest,rideReqLog);
			Ride ride = rideResponse.getBody();
			
			rideCallback.success(ride, rideResponse);
		}
	}
	
	private UberRidesService getUberRidesService(User user,String serviceMode){
		//TODO: User serviceMode to determine sync or async service
		return getUberRidesAsyncService(user);
	}
	private UberRidesService getUberRidesAsyncService(User user){
		BMRAuthService bmrAuthService = new BMRAuthService();
		UberRidesAsyncService uberRidesAsyncService = bmrAuthService.getActiveUberAsynchService(user.getUserSessionId());
		return uberRidesAsyncService;
	}
	
	private UberRidesService getUberSyncService(User user){
		BMRAuthService bmrAuthService = new BMRAuthService();
		UberRidesSyncService uberRidesSyncService = bmrAuthService.getActiveUberLoginSession(user.getUserSessionId());
		return uberRidesSyncService;
	}
	private RideRequestParameters getRideRequestParameters(RideRequest rideRequest) {
		return new RideRequestParameters.Builder()
							.setProductId(rideRequest.getProductId())
							.setPickupCoordinates(rideRequest.getStartLatitude(), rideRequest.getStartLongitude())
							.setDropoffCoordinates(rideRequest.getEndLatitude(), rideRequest.getEndLongitude())
							.setSurgeConfirmationId(rideRequest.getSurgeConfirmationId())
							.setPaymentMethodId(rideRequest.getPaymentMethodId()).build();
		
	}
	
	class RideCallback implements Callback<Ride>
	{

		RideRequestLog rideReqLog;
		RideRequest rideRequest;
		public RideCallback(RideRequest rideRequest,RideRequestLog rideReqLog) {
			this.rideRequest = rideRequest;
			this.rideReqLog = rideReqLog;
			this.rideReqLog.setEndTime(new Timestamp(System.currentTimeMillis()));
		}

		@Override
		public void failure(NetworkException exception) {
			saveFailedRideRequest(exception.getMessage())
		}

		@Override
		public void failure(ApiException exception) {
			
			//check the error
			SurgeError surgeError = null;
			List<UberError> errors = exception.getErrors();
				for(UberError error in errors){
					if(error instanceof SurgeError){
						surgeError = error;
					}
				}
				
				if(surgeError != null)
				{
					//We have surge pricing in place
					//Need to invoke surge flow
					/*
					 * Send an email to the user with surge details. Let the user accept the surge
					 * Once user accepts, the request will come to the callback controller's surgecallback action.
					 * There we will get the surge_confirmation_id from the query parameter.
					 *
					 *
					 */
					System.out.println("################ Surge #################");
					System.out.println("Surge Confirmation Id = "+ surgeError.getSurgeConfirmationId());
					System.out.println("########################################");
					
					RideRequest.withTransaction { tx ->
					
					rideRequest.setSurgeConfirmationId(surgeError.getSurgeConfirmationId());
					// updating the status of the request to failed. So that it will not be picked up again..just in case...
					// If user accepts the surge pricing then the rquest status is updated to Scheduled.
					rideRequest.setRequestStatus(RequestStatus.RequestFailed);
					rideRequest.save(failOnError:true,flush:true);
					}
					//Send mail about the ride status
					mailQueue.enqueueMailMessage(rideRequest);
				}
				else{
					saveFailedRideRequest(exception.getMessage());
				}
			
		}

		@Override
		public void failure(Throwable exception) {
			saveFailedRideRequest(exception.getMessage());
		}

		@Override
		public void success(Ride ride, Response<Ride> response) {
			//save returned id in RideRequest
			//Send mail about the ride status
			mailQueue.enqueueMailMessage(new RideResponse(rideRequest,ride));
			
			RideRequest.withTransaction { tx ->
				rideRequest.setUberRequestId(ride.getRideId())
				rideRequest.setRequestStatus(RequestStatus.RequestCompleted);
				rideRequest.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
				rideRequest.save(failOnError:true,flush:true);
				rideRequest.getRequester().setRidesInYear(rideRequest.getRequester().getRidesInYear() + 1);
				rideRequest.getRequester().save(failOnError:true,flush:true);
			}
			
			RideRequestLog.withTransaction { tx ->
				rideReqLog.setDetails("Success");
				rideReqLog.setRequestStatus(RequestStatus.RequestCompleted);
				rideReqLog.setEndTime(new Timestamp(System.currentTimeMillis()));
				rideReqLog.save(failOnError:true,flush:true);
			}
			if(BookMyRideConstants.reprocessedFailedRequest){
				rideRequest.removeBackOffRequestHandler();
			}	
		}
		
		private void saveFailedRideRequest(String details){
		
			RideRequestLog.withTransaction { tx ->
				rideReqLog.setDetails(details);
				rideReqLog.setRequestStatus(RequestStatus.RequestFailed);
				rideReqLog.setEndTime(new Timestamp(System.currentTimeMillis()));
				rideReqLog.save(failOnError:true,flush:true);
			}
			
			RideRequest.withTransaction { tx ->
				rideRequest.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
				rideRequest.setRequestStatus(RequestStatus.RequestFailed);
				rideRequest.save(failOnError:true,flush:true);
			}
			
			//reprocess Failed request
			rideRequest.reprocessRideRequest();
			
		}
	}
}
