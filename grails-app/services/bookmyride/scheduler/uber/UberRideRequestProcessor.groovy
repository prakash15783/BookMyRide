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
import bookmyride.scheduler.AbstractRideRequestProcessor

import com.uber.sdk.rides.client.Callback
import com.uber.sdk.rides.client.Response
import com.uber.sdk.rides.client.UberRidesAsyncService
import com.uber.sdk.rides.client.UberRidesService
import com.uber.sdk.rides.client.UberRidesSyncService
import com.uber.sdk.rides.client.error.ApiException
import com.uber.sdk.rides.client.error.NetworkException
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
				System.out.println("Processing Request : " +rideRequest.getProductId());

				//User uber sdk to process ride
				UberRidesService uberRidesService = getUberSyncService(rideRequest.getRequester());//getUberRidesService(rideRequest.getRequester(),"sync");
				RideRequestParameters rideRequestParameters = getRideRequestParameters(rideRequest);
				if(rideRequestParameters != null){
					requestRide(uberRidesService,rideRequestParameters,rideRequest,rideReqLog)
				}
			} catch (Throwable e) {
			
				System.out.println("Something went wrong: "+e.detailMessage);
				e.printStackTrace()
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
			//TODO: Use rideResponse for mailing logging.
			RideCallback rideCallback = new RideCallback(rideRequest,rideReqLog);
			Ride ride = rideResponse.getBody();
			System.out.println("Ride ID: " + ride.getRideId());
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
							.setStartLocation(new Location(rideRequest.getStartLatitude(), rideRequest.getStartLongitude()))
							.setEndLocation(new Location(rideRequest.getEndLatitude(), rideRequest.getEndLongitude()))
							.setSurgeConfirmationId(rideRequest.getSurgeConfirmationId()).build();
		
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
			saveFailedRideRequest(exception.getMessage());
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
			println"*************************************";
			println "1-------++++++++++++------------"+rideRequest.getStartAddress() + rideRequest.getRequestStatus();
			print "<<<Uber Request Id "+ride.getRideId()+">>>"
			
			System.out.println("RideRequest [requestId=" + rideRequest.getRequestId() + ", requester="
				+ rideRequest.getRequester() + ", startLatitude=" + rideRequest.getStartLatitude()
				+ ", startLongitude=" + rideRequest.getStartLongitude() + ", startAddress="
				);
			
			RideRequest.withTransaction { tx ->
				rideRequest.setUberRequestId(ride.getRideId())
				rideRequest.setRequestStatus(RequestStatus.RequestCompleted);
				rideRequest.save(failOnError:true,flush:true);
				rideRequest.getRequester().setRidesInYear(rideRequest.getRequester().getRidesInYear() + 1);
				rideRequest.getRequester().save(failOnError:true,flush:true);
			}
			
			RideRequestLog.withTransaction { tx ->
				rideReqLog.setDetails("Success");//TODO: save Details
				rideReqLog.save(failOnError:true,flush:true);
			}
			println "3-------++++++++++++------------"+rideRequest.getStartAddress() + rideRequest.getRequestStatus();
			
		}
		
		private void saveFailedRideRequest(String details){
			rideReqLog.setDetails(details);
			rideReqLog.save();
			
			rideRequest.setRequestStatus(RequestStatus.RequestCancelled);
			rideRequest.save();
		}
	}
}