package bookmyride

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.util.store.MemoryDataStoreFactory
import com.uber.sdk.rides.auth.OAuth2Credentials
import com.uber.sdk.rides.auth.OAuth2Credentials.Scope
import com.uber.sdk.rides.client.Session
import com.uber.sdk.rides.client.UberRidesAsyncService
import com.uber.sdk.rides.client.UberRidesServices
import com.uber.sdk.rides.client.UberRidesSyncService

class BMRAuthService {
	
	public static final String USER_SESSION_ID = "userSessionId";
	
		// Set your server's port and callback URL.
		//private static final int PORT = 8080;
		private static final String CALLBACK_URL = "/callback";
	
		// IMPORTANT: Before starting the server, make sure to add this redirect URI to your
		// application at developers.uber.com.
		private static final String REDIRECT_URI = "http://localhost:8080/BookMyRide" + CALLBACK_URL;

		static {
			System.setProperty("https.proxyHost", "www-proxy.us.oracle.com");
			System.setProperty("https.proxyPort", "80");
			
			System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
			System.setProperty("http.proxyPort", "80");
		}
	/**
	 * Creates an {@link OAuth2Credentials} object that can be used by any of the servlets.
	 *
	 * Throws an {@throws IOException} when no client ID or secret provided
	 */
	 static OAuth2Credentials createOAuth2Credentials() throws IOException {

		String clientId = "HHkH0421N9dMtPp4Rd7Tx6tEdriNLBRE";
		String clientSecret = "_diSmxvO3XAxzCvdSynp6k7h6Hp-yW0L_Cbr2rGu";
		ArrayList<Scope> scopes = new ArrayList<Scope>();
		scopes.add(Scope.HISTORY);
		scopes.add(Scope.PROFILE);
		scopes.add(Scope.REQUEST);
		

		return new OAuth2Credentials.Builder()
				.setCredentialDataStoreFactory(MemoryDataStoreFactory.getDefaultInstance())
				.setRedirectUri(REDIRECT_URI)
				.setClientSecrets(clientId, clientSecret)
				.setScopes(scopes)
				.build();
	}
	 
	 private OAuth2Credentials oAuth2Credentials;
	 private Credential credential;
	 private UberRidesSyncService uberRidesService;
 
	 
	 public UberRidesSyncService getActiveUberLoginSession(String userId)
	 {
		 if (oAuth2Credentials == null) {
			 oAuth2Credentials = BMRAuthService.createOAuth2Credentials();
		 }
 
		 credential = oAuth2Credentials.loadCredential(userId);
		 
		 print "User Id " + userId +" Credential : "+credential.getAccessToken();
 
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
	 
	 public UberRidesAsyncService getActiveUberAsynchService(String userId)
	 {
		 if (oAuth2Credentials == null) {
			 oAuth2Credentials = BMRAuthService.createOAuth2Credentials();
		 }
 
		 credential = oAuth2Credentials.loadCredential(userId);
 
		 if (credential != null && credential.getAccessToken() != null) {
			 if (uberRidesService == null) {
				 // Create the session
				 Session session = new Session.Builder()
						 .setCredential(credential)
						 .setEnvironment(BookMyRideConstants.ENVIRONMENT)
						 .build();
 
				 // Set up the Uber API Service once the user is authenticated.
				 uberRidesService = UberRidesServices.createAsync(session);
			 }
			 return uberRidesService;
			 
			 
		 } else {
			 return null;
		 }
		 
		 return null;
	 }

}
