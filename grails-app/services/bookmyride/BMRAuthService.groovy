package bookmyride

import com.google.api.client.util.store.MemoryDataStoreFactory;
import com.uber.sdk.rides.auth.OAuth2Credentials;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class BMRAuthService {
	
	public static final String USER_SESSION_ID = "userSessionId";
	
		// Set your server's port and callback URL.
		//private static final int PORT = 8080;
		private static final String CALLBACK_URL = "/Callback";
	
		// IMPORTANT: Before starting the server, make sure to add this redirect URI to your
		// application at developers.uber.com.
		private static final String REDIRECT_URI = "https://www.lifejour.com/BookMyRide" + CALLBACK_URL;

	/**
	 * Creates an {@link OAuth2Credentials} object that can be used by any of the servlets.
	 *
	 * Throws an {@throws IOException} when no client ID or secret provided
	 */
	 static OAuth2Credentials createOAuth2Credentials() throws IOException {

		String clientId = "HHkH0421N9dMtPp4Rd7Tx6tEdriNLBRE";
		String clientSecret = "_diSmxvO3XAxzCvdSynp6k7h6Hp-yW0L_Cbr2rGu";

		return new OAuth2Credentials.Builder()
				.setCredentialDataStoreFactory(MemoryDataStoreFactory.getDefaultInstance())
				.setRedirectUri(REDIRECT_URI)
				.setClientSecrets(clientId, clientSecret)
				.build();
	}

}
