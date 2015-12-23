package bookmyride

import org.springframework.context.ApplicationContext

import bookmyride.mail.IMailService
import bookmyride.mail.MailService
import bookmyride.scheduler.PollerTask
import bookmyride.scheduler.RequestPoller
import bookmyride.scheduler.RideScheduler

class ServiceLauncher {

	static void LaunchServices(){
		//StartMailing Service
		ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();
		IMailService mailService = (MailService)ctx.getBean("mailService");
		mailService.start();
		
		//Start RideScheduler
		RequestPoller requestPoller = new RequestPoller(new PollerTask());
		RideScheduler rideScheduler = new RideScheduler(requestPoller);
		rideScheduler.start();

		System.setProperty("https.proxyHost", "www-proxy.us.oracle.com");
		System.setProperty("https.proxyPort", "80");
	}
}
