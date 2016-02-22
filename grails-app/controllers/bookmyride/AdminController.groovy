package bookmyride

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

		}

		List<RideRequest> rideRequests = RideRequest.findAll();

		[userProfile:userProfile, requests: rideRequests]


	}
	
	def rideRequestLog(){
		
				UserProfile userProfile;
		
				uberRidesService = getActiveUberLoginSession();
				if(uberRidesService != null){
					// Fetch the user's profile.
					userProfile = uberRidesService.getUserProfile().getBody();
		
				}
		
				List<RideRequestLog> rideRequests = RideRequestLog.findAll();
		
				[userProfile:userProfile, requests: rideRequests]
		
		
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
