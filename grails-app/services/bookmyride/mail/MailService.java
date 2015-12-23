package bookmyride.mail;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import bookmyride.BookMyRideConstants;
import bookmyride.BookMyRideUtils;
import bookmyride.CommonDataStore;
import bookmyride.MailQueue;
import bookmyride.RideRequest;
import bookmyride.RideResponse;
import bookmyride.User;

import com.uber.sdk.rides.client.model.Ride;

public class MailService implements IMailService {
    private volatile boolean isRunning = false;
    private MailQueue mailQueue = (MailQueue)(CommonDataStore.getDataStore(BookMyRideConstants.MAIL_QUEUE));
    private MailSender mailSender;

    public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	private ScheduledExecutorService executorService;
    
    public void start(){
        if(!isRunning){
            isRunning = true;
            executorService = Executors.newSingleThreadScheduledExecutor();
            run();
            System.out.println("MailService Started...");
        }
    }

    public void stop(){
        if(isRunning){
            isRunning = false;
            executorService.shutdown();
            System.out.println("MailService shutdown...");
        }
    }

	public void run() {
		executorService.scheduleAtFixedRate(new Runnable() {
					@Override
					public void run() {
						try {
							if (mailQueue != null) {
								RideResponse rideResponse = mailQueue.takeMailMessage();
								System.out.println("Mailing message :"+rideResponse);
								processRideResponse(rideResponse);
							}else{ //Try to get the mailQueue from store.
								mailQueue = (MailQueue)(CommonDataStore.getDataStore(BookMyRideConstants.MAIL_QUEUE));
							}
						} catch (Throwable e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
					}
					
				},0, 5, TimeUnit.SECONDS);
	}
    
    private boolean processRideResponse(RideResponse rideResponse){
		User user = rideResponse.getRideRequest().getRequester();
		String mailMessage = getMailMessage(rideResponse);
		sendMail("noreply@bookmyride.com",user.getEmail(),"Booking Confirmation",mailMessage);
				
		return true;
    }
 
    private String getMailMessage(RideResponse rideResponse) {
    	Ride responseObject = (Ride)rideResponse.getResponseObject();
    	RideRequest rideReq = rideResponse.getRideRequest();
    	User user = rideResponse.getRideRequest().getRequester();
    	BookMyRideUtils utils = new BookMyRideUtils();
    	String mailMsg = utils.getMailFormat();
    	mailMsg = mailMsg.replace("${user}", "userName");//TODO: Complete all the details
    	mailMsg = mailMsg.replace("${pickUp}", "pickupaddress");
    	
    	System.out.println(mailMsg);
    	return mailMsg;
	}

	public void sendMail(String from, String to, String subject, String msg) {
		/*SimpleMailMessage message = new SimpleMailMessage();
		
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);*/
		try {
		JavaMailSenderImpl mimeMailSender =((JavaMailSenderImpl)(mailSender)); 
		
		MimeMessage mimeMsg = mimeMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
		helper.setTo(to);
		
			helper.setFrom(from);
		helper.setSubject(subject);
		helper.setText(msg);
		mimeMailSender.send(mimeMsg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    @Override
    protected void finalize() throws Throwable {
    	super.finalize();
    	stop();
    }
    
}