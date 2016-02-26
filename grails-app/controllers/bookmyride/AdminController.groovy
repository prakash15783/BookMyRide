package bookmyride

import java.text.SimpleDateFormat

import javax.servlet.http.HttpSession

import org.apache.commons.id.uuid.VersionFourGenerator

import com.google.api.client.auth.oauth2.Credential
import com.uber.sdk.rides.auth.OAuth2Credentials
import com.uber.sdk.rides.client.Session
import com.uber.sdk.rides.client.UberRidesServices
import com.uber.sdk.rides.client.UberRidesSyncService
import com.uber.sdk.rides.client.model.UserProfile

class AdminController {

	private OAuth2Credentials oAuth2Credentials;
	private Credential credential;
	private UberRidesSyncService uberRidesService;


	def history(){

		UserProfile userProfile;

		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			userProfile = uberRidesService.getUserProfile().getBody();
			if(userProfile == null){
				response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
			}
			
			
			SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date fromDate = null;
			
			if(params != null && params['fromdate'] != null && params['fromdate'] != ""){
				fromDate = isoFormat.parse(params['fromdate']);
			}
			
			Date toDate = null;
			
			if(params != null && params['todate'] != null && params['todate'] != ""){
				toDate = isoFormat.parse(params['todate']);
			}
			
			List<RideRequest> rideRequests = RideRequest.findAll("from RideRequest order by requestDate desc",[max:100]);
						
			if(fromDate != null && toDate != null){
				rideRequests = RideRequest.withCriteria {
					ge('requestDate',fromDate)
					le('requestDate',toDate)
				}
			}
			
			if(fromDate != null && toDate == null){
				rideRequests = RideRequest.withCriteria {
					ge('requestDate',fromDate)
				}
			}
			
			[userProfile:userProfile, requests: rideRequests]
		}
		else{
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}
	}
	
	def rideRequestLog(){
		
				UserProfile userProfile;
		
				uberRidesService = getActiveUberLoginSession();
				if(uberRidesService != null){
					// Fetch the user's profile.
					userProfile = uberRidesService.getUserProfile().getBody();
					if(userProfile == null){
						response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
					}
					
					SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date fromDate = null;
					
					if(params != null && params['fromdate'] != null && params['fromdate'] != ""){
						fromDate = isoFormat.parse(params['fromdate']);
					}
					
					Date toDate = null;
					
					if(params != null && params['todate'] != null && params['todate'] != ""){
						toDate = isoFormat.parse(params['todate']);
					}
					
					List<RideRequestLog> rideRequests = RideRequestLog.findAll("from RideRequestLog order by endTime desc",[max:100]);
					
					
					if(fromDate != null && toDate != null){
						rideRequests = RideRequestLog.withCriteria {
							ge('startTime',fromDate)
							le('endTime',toDate)
						}
					}
					
					if(fromDate != null && toDate == null){
						rideRequests = RideRequestLog.withCriteria {
							ge('startTime',fromDate)
						}
					}
					
					
							[userProfile:userProfile, requests: rideRequests]
				}
		
				else{
					response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
				}
		
		
	}
	
	def webhookLog(){
		
				UserProfile userProfile;
		
				uberRidesService = getActiveUberLoginSession();
				if(uberRidesService != null){
					// Fetch the user's profile.
					userProfile = uberRidesService.getUserProfile().getBody();
					if(userProfile == null){
						response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
					}
					
					
				/*	SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy/MM/dd");
					Date fromDate = null;
					
					if(params != null && params['fromdate'] != null && params['fromdate'] != ""){
						fromDate = isoFormat.parse(params['fromdate']);
					}
					
					Date toDate = null;
					
					if(params != null && params['todate'] != null && params['todate'] != ""){
						toDate = isoFormat.parse(params['todate']);
					}
					*/
					List<WebhookEvent> webhookEvents = WebhookEvent.findAll([sort:'eventTime',order:'desc',max:100]);
					
					
					/*if(fromDate != null && toDate != null){
						webhookEvents = RideRequest.withCriteria {
							ge('requestDate',fromDate)
							le('requestDate',toDate)
						}
					}
					
					if(fromDate != null && toDate == null){
						webhookEvents = RideRequest.withCriteria {
							ge('requestDate',fromDate)
						}
					}*/
					
					[userProfile:userProfile, webhookEvents: webhookEvents]
				}
				else{
					response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
				}
				
				
		
		
			}

	private UberRidesSyncService getActiveUberLoginSession()
	{
		if (oAuth2Credentials == null) {
			oAuth2Credentials = BMRAuthService.createOAuth2Credentials();
		}

		// Load the user from their user ID (derived from the request).
		HttpSession httpSession = request.getSession(true);
		if (httpSession.getAttribute(BMRAuthService.USER_SESSION_ID) == null) {
			httpSession.setAttribute(BMRAuthService.USER_SESSION_ID, VersionFourGenerator.getInstance().nextUUID().toString());
		}
		credential = oAuth2Credentials.loadCredential(httpSession.getAttribute(BMRAuthService.USER_SESSION_ID).toString());

		if (credential != null && credential.getAccessToken() != null) {
			if (uberRidesService == null) {
				// Create the session
				Session session = new Session.Builder()
						.setCredential(credential)
						.setEnvironment(BookMyRideConstants.ENVIRONMENT)
						.build();

				// Set up the Uber API Service once the user is authenticated.
				uberRidesService = UberRidesServices.createSync(session);
			}
			return uberRidesService;

		} else {
			return null;
		}

		return null;
	}
	


}