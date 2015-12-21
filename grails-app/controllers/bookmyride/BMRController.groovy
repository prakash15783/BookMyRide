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
import java.text.SimpleDateFormat
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.id.uuid.*;

class BMRController {

	private OAuth2Credentials oAuth2Credentials;
	private Credential credential;
	private UberRidesSyncService uberRidesService;

	def login() {
		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			UserProfile userProfile = uberRidesService.getUserProfile().getBody();

			User user = User.findByUuid(userProfile.getUuid());

			// If this is a new user then add it to the database
			if(user == null)
			{
				user = new User();
				user.setFirstName(userProfile.getFirstName());
				user.setLastName(userProfile.getLastName());
				user.setEmail(userProfile.getEmail());
				user.setPicture(userProfile.getPicture());
				user.setPromoCode(userProfile.getPromoCode());
				user.setUuid(userProfile.getUuid());
				user.setAccessToken(credential.getAccessToken());
				user.setRefreshToken(credential.getRefreshToken());
				user.setTokenExpiry(credential.getExpiresInSeconds());
				user.save();
			}

			// save the logged in user in the session
			session.setAttribute("current_user", user);

			// check if user is already logged into uber and if yes then redirect him to the welcome page.
			redirect(action: "request");
		}
		else{
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}

	}

	def logout() {
		uberRidesService = null;
		credential = null;
		request.getSession(true).invalidate();
		redirect(action: "index");
	}


	def request()
	{

		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			UserProfile userProfile = uberRidesService.getUserProfile().getBody();
			User user = User.findByUuid(userProfile.getUuid());
			if(user==null)
			{
				// if it is the first time user has visited this site and the server just started...
				// we need to keep user details in the database..
				redirect(action: "login");
			}

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
			redirect(action: "login");
		}
		// else show the index page

	}

	def confirm()
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


	def submitRequest()
	{
		uberRidesService = getActiveUberLoginSession();
		UserProfile userProfile;
		if(uberRidesService != null){
			// Fetch the user's profile.
			userProfile = uberRidesService.getUserProfile().getBody();
		}

		float startLatitude = Float.parseFloat(params['pickup_latitude']);
		float startLongitude = Float.parseFloat(params['pickup_longitude']);
		String startAddress = params['pickup_address'];


		float endLatitude = Float.parseFloat(params['drop_latitude']);
		float endLongitude = Float.parseFloat(params['drop_longitude']);
		String endAddress = params['drop_address'];

		String productId = params['vehicle-select'];

		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		isoFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		Date requestDate = isoFormat.parse(params['datetime']);


		RideRequest rideRequest = new RideRequest();
		rideRequest.setStartLatitude(startLatitude);
		rideRequest.setStartLongitude(startLongitude);
		rideRequest.setStartAddress(startAddress);
		rideRequest.setEndLatitude(endLatitude);
		rideRequest.setEndLongitude(endLongitude);
		rideRequest.setEndAddress(endAddress);
		rideRequest.setRequester(User.findByUuid(userProfile.getUuid()));
		rideRequest.setProductId(productId);
		rideRequest.setRequestDate(requestDate);
		rideRequest.setRequestStatus(RequestStatus.RequestScheduled);
		String requestId = VersionFourGenerator.getInstance().nextUUID().toString();
		rideRequest.setRequestId(requestId);

		rideRequest.save();

		redirect(action: "queue");

		return;
	}

	def cancelRequest()
	{
		String request_id = params['request_id'];
		RideRequest rideRequest = RideRequest.findById(request_id);
		rideRequest.setRequestStatus(RequestStatus.RequestCancelled);
		rideRequest.save();

		redirect(action: "queue");

		return;
	}

	def queue(){

		UserProfile userProfile;

		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			userProfile = uberRidesService.getUserProfile().getBody();

		}

		List<RideRequest> rideRequests = RideRequest.findAllByRequesterAndRequestStatus(User.findByUuid(userProfile?.getUuid()), RequestStatus.RequestScheduled);

		[userProfile:userProfile, requests: rideRequests]


	}


	def history(){

		UserProfile userProfile;

		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			userProfile = uberRidesService.getUserProfile().getBody();

		}

		List<RideRequest> rideRequests = RideRequest.findAllByRequester(User.findByUuid(userProfile?.getUuid()));

		[userProfile:userProfile, requests: rideRequests]


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


	def getProduct()
	{
		String product_id = params['product_id'];

		uberRidesService = getActiveUberLoginSession();

		Product product =  uberRidesService.getProduct(product_id).getBody();


		String vehicle_table = "Selected vehicle: <br/><table> <th>Name(Capacity)</th> <th>Description</th> <th>Image</th>";

		vehicle_table = vehicle_table + "<tr><td>"+ product.getDisplayName() + " ("+product.getCapacity()+")</td>" + "<td>" + product.getDescription() + "</td>" + "<td><img src='" + product.getImage() + "' height='42' width='60'/></td></tr>";


		vehicle_table = vehicle_table + "</table>"

		render vehicle_table;
	}
	
	def faq()
	{
		UserProfile userProfile;
		
				uberRidesService = getActiveUberLoginSession();
				if(uberRidesService != null){
					// Fetch the user's profile.
					userProfile = uberRidesService.getUserProfile().getBody();
				}
				
				[userProfile:userProfile]
		
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
