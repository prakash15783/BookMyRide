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

    def login() {
		
		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// check if user is already logged into uber and if yes then redirect him to the welcome page.
			redirect(action: "request");
			
		} else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}
		
    }
	
	def logout() {
		uberRidesService = null;
		credential = null;
		request.getSession(true).invalidate();
		redirect(action: "index");
	}
	
	/*
	
	def listProducts(){
		float latitude = Float.parseFloat(params?.latitude);
		float longitude = Float.parseFloat(params?.longitude);
		
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

			ProductsResponse productResponse = uberRidesService.getProducts(latitude, longitude).getBody();
			List<Product> products = productResponse.getProducts();
			
			[products: products]
			
		} else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}
		
	}
	*/
	
	def request()
	{
		
		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			UserProfile userProfile = uberRidesService.getUserProfile().getBody();
			
			[userProfile:userProfile, credential:credential]
			
		} else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}
		
	}
	
	def index()
	{	
		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// check if user is already logged into uber and if yes then redirect him to the welcome page.
			redirect(action: "request");
		}
		// else show the index page
		
	}
	
	def confirm()
	{
		render "You sibmitted following details for ride request: <br/><br/>"
		render "Pickup Latitude: " + params['pickup_latitude'] + "<br/>";
		render "Pickup Longitude: " + params['pickup_longitude'] + "<br/>";
		render "Drop Latitude: " + params['drop_latitude'] + "<br/>";
		render "Drop Longitude: " + params['drop_longitude'] + "<br/>";
		render "Pickup Address: " + params['pickup_address'] + "<br/>";
		render "Drop Address: " + params['drop_address'] + "<br/>";
		render "Vehicle Type: " + params['vehicle-select'] + "<br/>";
		
		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			UserProfile userProfile = uberRidesService.getUserProfile().getBody();
			
			render "Requester ID: "+ userProfile.getUuid() + "<br/>";
		}
		
		render "Pickup Date and Time: " + params['datetime'];
		
		return;
	}
	
	def getProducts()
	{
		float latitude = Float.parseFloat(params['pickup_latitude']);
		float longitude = Float.parseFloat(params['pickup_longitude']);
		String pickup_address = params['pickup_address'];
		
		
		uberRidesService = getActiveUberLoginSession();
		
		ProductsResponse productResponse = uberRidesService.getProducts(latitude, longitude).getBody();
		List<Product> products = productResponse.getProducts();
		
		String vehicle_table = "Vehicles available at pickup location "+pickup_address+" are: <br/><table> <div class='row uniform 50%'><div class='4u 12u(narrower)'><th>Name(Capacity)</th> <th>Description</th> <th>Image</th>";
		for(Product p in products)
		{
			vehicle_table = vehicle_table + "<tr><td><input type='radio' id='"+p.getProductId()+"' name='vehicle-select' value='"+p.getProductId()+"'><label for='"+p.getProductId()+"'>"+ p.getDisplayName() + " ("+p.getCapacity()+")</label></td>" + "<td>" + p.getDescription() + "</td>" + "<td><img src='" + p.getImage() + "' height='42' width='60'/></td></tr>";
		}
		
	
		vehicle_table = vehicle_table + "</div></div></table>"
		
		render vehicle_table;
	}
	
	private UberRidesSyncService getActiveUberLoginSession()
	{
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
			return uberRidesService;
			
		} else {
			return null;
		}
		
		return null;
	}

    
}
