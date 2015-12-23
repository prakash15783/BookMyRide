package bookmyride

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
		response.sendRedirect("/BookMyRide/request");

	}

}
