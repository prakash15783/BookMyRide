package bookmyride

import java.sql.Timestamp
import java.text.SimpleDateFormat

import javax.net.ssl.HttpsURLConnection
import javax.servlet.http.HttpSession

import org.apache.commons.id.uuid.*

import com.google.api.client.auth.oauth2.Credential
import com.uber.sdk.rides.auth.OAuth2Credentials
import com.uber.sdk.rides.client.Response
import com.uber.sdk.rides.client.Session
import com.uber.sdk.rides.client.UberRidesServices
import com.uber.sdk.rides.client.UberRidesSyncService
import com.uber.sdk.rides.client.UberRidesServices.LogLevel
import com.uber.sdk.rides.client.model.PaymentMethod
import com.uber.sdk.rides.client.model.PaymentMethodsResponse
import com.uber.sdk.rides.client.model.Product
import com.uber.sdk.rides.client.model.ProductsResponse
import com.uber.sdk.rides.client.model.UserProfile

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
				user.setUserSessionId(session.getAttribute(BMRAuthService.USER_SESSION_ID));
				if(userProfile.getEmail().equalsIgnoreCase("raju.bhucs@gmail.com") || userProfile.getEmail().equalsIgnoreCase("prakash15783@gmail.com"))
				{
					user.setAdmin(true);
				}
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
			else
			{
				user.setUserSessionId(session.getAttribute(BMRAuthService.USER_SESSION_ID));
				user.setPicture(userProfile.getPicture());
				user.save();
				
				// save the logged in user in the session
				session.setAttribute("current_user", user);
			}
			/*
			 * Disabling this for now.. Will enable this when payment gateway is setup
			 * 
			if(user?.getRidesInYear() == 1 && !user?.isPaymentDone())
			{
				redirect(action: "payment");
			}
			*/

			[userProfile:userProfile, credential:credential]

		} else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}

	}
	
	def notice()
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
	
	def payment()
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
	
	def pay()
	{
		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			UserProfile userProfile = uberRidesService.getUserProfile().getBody();
			User user = User.findByUuid(userProfile.getUuid());
			/*
			 * TODO: Logic for payment goes here
			 */
			user.setPaymentDone(true);

		}
		
		redirect(action: "request");
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

	def contactus()
	{
		//do nothing

	}
	
	def thankyou()
	{
		//do nothing

	}
	def submitcontactus()
	{
		
		String name = params['name'];
		String email = params['email'];

		String message = params['message'];
		String category = params['category'];

		ContactUs contactUs = new ContactUs();
		contactUs.setName(name);
		contactUs.setEmail(email);
		contactUs.setMessage(message)

		contactUs.save();
		//Enqueue for mailing
		MailQueue mailQueue = (MailQueue)CommonDataStore.getDataStore(BookMyRideConstants.MAIL_QUEUE);
		mailQueue.enqueueMailMessage(contactUs);
		
		redirect(action: "thankyou");

		return;

	}
	
	def confirm()
	{
		String startAddress = params['pickup_address'];
		String endAddress = params['drop_address'];
		
		if((!startAddress.endsWith("India")) || (!endAddress.endsWith("India")))
		{
			redirect(action: "notice");
		}
		String captcha = params['g-recaptcha-response'];
		
		if(captcha?.empty)
		{
			redirect(action: "request")
		}else if(!validateCaptcha(captcha)){
			redirect(action: "request")
		}
		
		
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
		else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}

		float startLatitude = Float.parseFloat(params['pickup_latitude']);
		float startLongitude = Float.parseFloat(params['pickup_longitude']);
		String startAddress = params['pickup_address'];


		float endLatitude = Float.parseFloat(params['drop_latitude']);
		float endLongitude = Float.parseFloat(params['drop_longitude']);
		String endAddress = params['drop_address'];

		String productId = params['vehicle-select'];
		String paymentMethodId = params['payment-select'];
		
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
		rideRequest.setCreatedTimestamp(new Timestamp(System.currentTimeMillis()));
		rideRequest.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		rideRequest.setPaymentMethodId(paymentMethodId);
		
		rideRequest.save();

		//Enqueue for mailing
		MailQueue mailQueue = (MailQueue)CommonDataStore.getDataStore(BookMyRideConstants.MAIL_QUEUE);
		mailQueue.enqueueMailMessage(new RideResponse(rideRequest, null));
				
		redirect(action: "queue");

		return;
	}

	def cancelRequest()
	{
		String request_id = params['request_id'];
		RideRequest rideRequest = RideRequest.findById(request_id);
		rideRequest.setRequestStatus(RequestStatus.RequestUserCancelled);
		rideRequest.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
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

		}else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}

		List<RideRequest> rideRequests = RideRequest.findAllByRequesterAndRequestStatus(User.findByUuid(userProfile?.getUuid())
			, RequestStatus.RequestScheduled,[sort:'requestDate',order:'desc']);

		[userProfile:userProfile, requests: rideRequests]


	}


	def history(){

		UserProfile userProfile;

		uberRidesService = getActiveUberLoginSession();
		if(uberRidesService != null){
			// Fetch the user's profile.
			userProfile = uberRidesService.getUserProfile().getBody();

		}else {
			response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
		}

		List<RideRequest> rideRequests = RideRequest.findAllByRequester(User.findByUuid(userProfile?.getUuid()),[sort:'requestDate',order:'desc']);

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

		String vehicle_table = "Vehicles available at pickup location <b>"+pickup_address+"</b> are: <br/><table> <div class='row uniform 50%'><div class='4u 12u(narrower)'><th>Name(Capacity)</th> <th>Description</th> <th>Image</th>";
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


		String vehicle_table = "<b>Selected vehicle: </b><br/><table> <th>Name(Capacity)</th> <th>Description</th> <th>Image</th>";

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
				}else {
					response.sendRedirect(oAuth2Credentials.getAuthorizationUrl());
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
//				uberRidesService = UberRidesServices.createSync(session);
//				uberRidesService =	(UberRidesSyncService)new UberRidesServices.Builder().setLogLevel(LogLevel.FULL).setSession(session).build();
				uberRidesService =	(UberRidesSyncService)new UberRidesServices.Builder().setSession(session).build();

				return uberRidesService;

			} else {
				return null;
			}

			return null;
		}
	}
	
	private boolean validateCaptcha(String captcha) throws Exception {

		String url = "https://www.google.com/recaptcha/api/siteverify?secret=6LehlxkTAAAAAEIvKkHpftXLn_EC3-8B4tp4X41M&response="+captcha;
		
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();

		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer res = new StringBuffer();

		while ((inputLine = br.readLine()) != null) {
			res.append(inputLine);
		}
		br.close();
		
		if(res.toString().contains("\"success\": true"))
		{
			return true
		}
		if(res.toString().contains("\"success\":true"))
		{
			return true
		}
		
		return false;
	}

	def getPaymentMethods()
	{
		uberRidesService = getActiveUberLoginSession();

		PaymentMethodsResponse paymentMethodResponse = uberRidesService.getPaymentMethods().getBody();
		List<PaymentMethod> paymentMethods = paymentMethodResponse.getPaymentMethods();

		String payment_method_table = "";
		for(PaymentMethod p in paymentMethods)
		{
			payment_method_table = payment_method_table + "<div class='3u 12u(medium)'><input type='radio' id='"+p.getPaymentMethodId()+"' name='payment-select' value='"+p.getPaymentMethodId()+"'>"+
			"<label for='"+p.getPaymentMethodId()+"'>"+ p.getType() + "</label></div>";
		}
									
		payment_method_table = "Payment Method for you : <br/>" + payment_method_table

		render payment_method_table;
	}
	
	def getPaymentMethod()
	{
		String payment_id = params['payment_id'];
		uberRidesService = getActiveUberLoginSession();

		PaymentMethodsResponse paymentMethodResponse = uberRidesService.getPaymentMethods().getBody();
		List<PaymentMethod> paymentMethods = paymentMethodResponse.getPaymentMethods();

		String payment_method_table = "<b>Selected Payment Method :</b> ";
		for(PaymentMethod p in paymentMethods)
		{
			if(p.getPaymentMethodId().equalsIgnoreCase(payment_id)){
				payment_method_table = payment_method_table + p.getType();
				break;
			}
		}
		render payment_method_table;
	}


}
