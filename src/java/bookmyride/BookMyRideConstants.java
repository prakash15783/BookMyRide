package bookmyride;

import com.uber.sdk.rides.client.Session.Environment;

public class BookMyRideConstants {

	public static final String MAIL_QUEUE = "MAIL_QUEUE";
	public static final String REQUEST_QUEUE="REQUEST_QUEUE";
	public static final Environment ENVIRONMENT;//Decide it through a property
	static{
		
		if (grails.util.Environment.getCurrent() == grails.util.Environment.DEVELOPMENT) {
			// insert Development environment specific code here
			ENVIRONMENT = Environment.SANDBOX;
			
			System.out.println("##############################################");
			System.out.println("   Setting Environment DEVELOPMENT SANDBOX   ");
			System.out.println("##############################################");
		} 
		else if (grails.util.Environment.getCurrent() == grails.util.Environment.TEST) {
			// insert Test environment specific code here
			ENVIRONMENT = Environment.SANDBOX;
			
			System.out.println("##############################################");
			System.out.println("   Setting Environment TEST SANDBOX    ");
			System.out.println("##############################################");
		} 
		else if (grails.util.Environment.getCurrent() == grails.util.Environment.PRODUCTION) {
			// insert Production environment specific code here
			ENVIRONMENT = Environment.PRODUCTION;
			
			System.out.println("##############################################");
			System.out.println("        Setting Environment PRODUCTION        ");
			System.out.println("##############################################");
		}
		else{
			// other environments
			ENVIRONMENT = Environment.SANDBOX;
			
			System.out.println("##############################################");
			System.out.println("      Setting Environment OTHER SANDBOX     ");
			System.out.println("##############################################");
		}
	}
}
