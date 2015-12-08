package bookmyride

import com.google.api.client.auth.oauth2.Credential;
import com.uber.sdk.rides.auth.OAuth2Credentials;
import com.uber.sdk.rides.client.Session;
import com.uber.sdk.rides.client.UberRidesServices;
import com.uber.sdk.rides.client.UberRidesSyncService;
import com.uber.sdk.rides.client.model.Product
import com.uber.sdk.rides.client.model.ProductsResponse
import com.uber.sdk.rides.client.model.UserProfile;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

class BMRController {
	
	private OAuth2Credentials oAuth2Credentials;
	private Credential credential;
	private UberRidesSyncService uberRidesService;

    def index() {
		
		if (oAuth2Credentials == null) {
			oAuth2Credentials = BMRAuthService.createOAuth2Credentials();
		}

		// Load the user from their user ID (derived from the request).
		HttpSession httpSession = request.getSession(true);
		if (httpSession.getAttribute(BMRAuthService.USER_SESSION_ID) == null) {
			httpSession.setAttribute(BMRAuthService.USER_SESSION_ID, new Random().nextLong());
		}
		credential = oAuth2Credentials.loadCredential(httpSession.getAttribute(BMRAuthService.USER_SESSION_ID).toString());

		if (credential != null && credential.getAccessToken() != null) {
			if (uberRidesService == null) {
				// Create the session
				Session session = new Session.Builder()
						.setCredential(credential)
						.setEnvironment(Session.Environment.PRODUCTION)
						.build();

				// Set up the Uber API Service once the user is authenticated.
				uberRidesService = UberRidesServices.createSync(session);
			}

			// Fetch the user's profile.
			UserProfile userProfile = uberRidesService.getUserProfile().getBody();
	
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			
			//display some information to the user
			render userProfile.getFirstName().toUpperCase() + ", Welcome to BookMyRide <br/><br/>"
			render "Your email id registered with Uber is "+ userProfile.getEmail() + "<br/><br/>"
			render "Your Access Token is " + credential.getAccessToken()
			render "<br/><br/>Your Refresh Token is " + credential.getRefreshToken()
			render "<br/><br/>Token Expiry in Secs is " + credential.getExpiresInSeconds()
			render "<br/><br/>list of products from latitude 37.775 and longitude -122.417 are following:<br/>"
			render "------------------------------------------------------"
			listProducts(37.775f, -122.417f);
			
		} else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}
		
		
    }
	
	def listProducts(float latitude, float longitude){
		ProductsResponse products = uberRidesService.getProducts(latitude, longitude).getBody();
		
		for(Product p in products.getProducts())
		{
			render "<br/>Product Name: " + p.getDisplayName();
			render "<br/>Product Description: " + p.getDescription();
			render "<br/>Product Capacity: " + p.getCapacity();
			render "<br/>Product Image: <img src='" + p.getImage() + "'/>"; 
			render "<br/>Product Id: " + p.getProductId();
			render "<br/>------------------------------------------------------"
		}
		
	}

    
}
