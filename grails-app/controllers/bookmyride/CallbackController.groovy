package bookmyride

import grails.converters.JSON
import groovy.json.JsonSlurper

import java.security.InvalidKeyException
import java.sql.Timestamp

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import javax.servlet.http.HttpSession

import org.apache.commons.id.uuid.VersionFourGenerator

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl
import com.uber.sdk.rides.auth.OAuth2Credentials


class CallbackController {

	private OAuth2Credentials oAuth2Credentials;

	def callback(){
		if (oAuth2Credentials == null) {
			oAuth2Credentials = BMRAuthService.createOAuth2Credentials();
		}

		HttpSession httpSession = request.getSession(true);
		if (httpSession.getAttribute(BMRAuthService.USER_SESSION_ID) == null) {
			httpSession.setAttribute(BMRAuthService.USER_SESSION_ID, VersionFourGenerator.getInstance().nextUUID().toString());
		}

		String requestUrl = request.getRequestURL().append('?').append(request.getQueryString()).toString();
		AuthorizationCodeResponseUrl authorizationCodeResponseUrl =
				new AuthorizationCodeResponseUrl(requestUrl);

		if (authorizationCodeResponseUrl.getError() != null) {
			throw new IOException("Received error: " + authorizationCodeResponseUrl.getError());
		} else {
			// Authenticate the user and store their credential with their user ID (derived from
			// the request).
			if (httpSession.getAttribute(BMRAuthService.USER_SESSION_ID) == null) {
				httpSession.setAttribute(BMRAuthService.USER_SESSION_ID, VersionFourGenerator.getInstance().nextUUID().toString());
			}
			String authorizationCode = authorizationCodeResponseUrl.getCode();
			oAuth2Credentials.authenticate(authorizationCode, httpSession.getAttribute(BMRAuthService.USER_SESSION_ID).toString());
		}
		response.sendRedirect("/request");

	}

	def webhook(){
		
		String env = request.getHeader("X-Environment");
		String hashedSignature = request.getHeader("X-Uber-Signature");
		String body = request.reader.text;
		
		System.out.println("#########  Recieved webhook call from UBER ##############");
		System.out.println("#########  Webhook message body ##############");
		System.out.println(body);
		System.out.println("##############################################");
		
		if(hashedSignature.equals(hmac_sha256(BMRAuthService.getClientSecret(),body))){
			//persist it in database, send notification
			System.out.println("##############################################");
			System.out.println("           Updating Webhook Table           ");
			System.out.println("##############################################");
			
			def jsonSlurper = new JsonSlurper()
			def parsedData = jsonSlurper.parseText(body);
			//Get webhook with the eventId
			WebhookEvent eventInDb = WebhookEvent.findByEventId(parsedData.event_id);
			
			if(eventInDb == null){
				WebhookEvent event = new WebhookEvent();
				event.setEventId(parsedData.event_id);
				event.setEventTime(parsedData.event_time);
				event.setEventType(parsedData.event_type);
				event.setResourceHref(parsedData.resource_href);
				event.save(failOnError:true);
				
				WebhookEventMeta meta = new WebhookEventMeta();
				meta.setResourceId(parsedData.meta.resource_id);
				meta.setResourceType(parsedData.meta.resource_type);
				meta.setStatus(parsedData.meta.status);
				meta.setWebhookEvent(event);
				meta.save(failOnError:true);
				
				//Get RideRequest and update the status
				RideRequest rideRequest = RideRequest.findByUberRequestId(event.getEventId());
				if(rideRequest != null){
					rideRequest.setRequestStatus(RequestStatus.valueOf(meta.getStatus()));
					rideRequest.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
					rideRequest.save();
					
					//Do reprocessing in case of failed response.
					/*if(BookMyRideConstants.reprocessedFailedRequest){
						rideRequest.reprocessRideRequest();
					}
					else{
						rideRequest.removeBackOffRequestHandler();
					}
					*/
				}
			}else{
				System.out.println("##############################################");
				System.out.println("          Event already available in database !!         ");
				System.out.println("##############################################");
		}
			
			
			/*
			 
			 {
				 "event_id": "3a3f3da4-14ac-4056-bbf2-d0b9cdcb0777",
				 "event_time": 1427343990,
				 "event_type": "requests.status_changed",
				 "meta": {
					 "resource_id": "2a2f3da4",
					 "resource_type": "request",
					 "status": "in_progress"
				 },
				 "resource_href": "https://api.uber.com/v1/requests/2a2f3da4"
			 }
			 */
			
		} else{
		System.out.println("##############################################");
		System.out.println("          Webhook Hash is not same !!         ");
		System.out.println("##############################################");
		}

	}
	
	
	def surgecallback(){
		
		// if control comes here..it means that user has accepted the surge confirmation and proceed with the ride request
		String surge_confirmation_id = params?.surge_confirmation_id;
		
		System.out.println("############################################");
		System.out.println(" Surge Confirmation Id = "+ surge_confirmation_id);
		System.out.println("############################################");
		
		// We saved earlier request in db using the confirmation id found in error message.
		// Find the earlier request and set surge_confirmation_id in it.
		// Schedule the request for 2 minutes from now.
		RideRequest rideRequest = RideRequest.findBySurgeConfirmationId(surge_confirmation_id);
		
		/*
		 * Find the current time and add two minutes to it
		 */
		Calendar date = Calendar.getInstance();
		long current= date.getTimeInMillis();
		Date afterAddingTwoMins=new Date(current + (2 * 60000));
		
		rideRequest.setRequestDate(afterAddingTwoMins);
		rideRequest.setRequestStatus(RequestStatus.RequestScheduled);
		rideRequest.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		
		rideRequest.save();
		// Now the request is scheduled for booking.
		
	}

	/**
	 * @param secretKey
	 * @param data
	 * @return HMAC/SHA256 representation of the given string
	 */
	private def hmac_sha256(String secretKey, String data) {
		try {  SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256")
			Mac mac = Mac.getInstance("HmacSHA256")
			mac.init(secretKeySpec)
			byte[] digest = mac.doFinal(data.getBytes("UTF-8"))
			return byteArrayToString(digest)
		} catch (InvalidKeyException e) {  throw new RuntimeException("Invalid key exception while converting to HMac SHA256")
		}
	}

	private def byteArrayToString(byte[] data) {
		BigInteger bigInteger = new BigInteger(1, data)
		String hash = bigInteger.toString(16)
		//Zero pad it
		while (hash.length() < 64) {
			hash = "0" + hash
		}
		return hash
	}

}
