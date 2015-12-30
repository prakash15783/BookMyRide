package bookmyride;

import com.uber.sdk.rides.client.Session.Environment;

public class BookMyRideConstants {

	public static final String MAIL_QUEUE = "MAIL_QUEUE";
	public static final String REQUEST_QUEUE="REQUEST_QUEUE";
	public static final Environment ENVIRONMENT;//Decide it through a property
	static{
		String env = System.getProperty("env");
		if(env != null && env.equalsIgnoreCase("prod")){
			ENVIRONMENT = Environment.PRODUCTION;
		}else{
			ENVIRONMENT = Environment.SANDBOX;
		}
	}
}
