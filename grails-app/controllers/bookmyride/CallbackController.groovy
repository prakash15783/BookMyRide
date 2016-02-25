package bookmyride

import grails.converters.JSON
import groovy.json.JsonSlurper

import java.security.InvalidKeyException

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
		if(hashedSignature.equals(hmac_sha256(BMRAuthService.getClientSecret(),body))){
			//persist it in database, send notification
			
			def jsonSlurper = new JsonSlurper()
			def parsedData = jsonSlurper.parseText(body);
			WebhookEvent event = new WebhookEvent();
			event.setEventId(parsedData.event_id);
			event.setEventTime(parsedData.event_time);
			event.setEventType(parsedData.event_type);
			event.setResourceHref(parsedData.resource_href);
			
			WebhookEventMeta meta = new WebhookEventMeta();
			meta.setResourceId(parsedData.meta.resource_id);
			meta.setResourceType(parsedData.meta.resource_type);
			meta.setStatus(parsedData.meta.status);
			event.setMeta(meta)
			
			event.save();
			//If status of an event changes then send notification.
			
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
			
		}

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
