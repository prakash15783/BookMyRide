package bookmyride.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import bookmyride.BookMyRideConstants;
import bookmyride.CommonDataStore;
import bookmyride.ContactUs;
import bookmyride.MailQueue;
import bookmyride.Mailable;
import bookmyride.RideRequest;
import bookmyride.RideResponse;
import bookmyride.User;

import com.uber.sdk.rides.client.model.Ride;

public class MailService implements IMailService {
    private static final String JIFFGO_FROM = "jiffGo<contact@jiffgo.com>";
	private volatile boolean isRunning = false;
    private MailQueue mailQueue = (MailQueue)(CommonDataStore.getDataStore(BookMyRideConstants.MAIL_QUEUE));
    private MailSender mailSender;
    private VelocityEngine velocityEngine;


	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
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
								Mailable rideResponse = mailQueue.takeMailMessage();
								System.out.println("Mailing message :"+rideResponse);
								if(rideResponse instanceof RideResponse){
									processRideResponse((RideResponse)rideResponse);
								}
								else if(rideResponse instanceof ContactUs){
									processContactUs((ContactUs)rideResponse);
								}
								else if(rideResponse instanceof RideRequest){
									System.out.println("################ Surge EMAIL PREPROCESS #################");
									System.out.println("Preparing for surge email");
									System.out.println("########################################");
									processSurgeEmail((RideRequest)rideResponse);
								}
								
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
    
	private void processContactUs(ContactUs contactUs) {
		Map model = new HashMap();	          
		model.put("contactUs", contactUs);
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "resources/contactUsTemplate.vm", "UTF-8", model);
		
		String subject = "Thank you";
		try {
			boolean status = sendMimeMail(JIFFGO_FROM,JIFFGO_FROM,subject,text);//mail to contact@jiffgo.com
			status = sendMimeMail(JIFFGO_FROM,contactUs.getEmail(),subject,text);//mail to user.
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processSurgeEmail(RideRequest rideRequest) {
		Map model = new HashMap();	          
		model.put("rideRequest", rideRequest);
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "resources/surgeEmailTemplate.vm", "UTF-8", model);
		
		String subject = "Surge Confirmation";
		try {
			System.out.println("################ Surge #################");
			System.out.println("Sending surge email");
			System.out.println(text);
			System.out.println("########################################");
			boolean status = sendMimeMail(JIFFGO_FROM,JIFFGO_FROM,subject,text);//mail to contact@jiffgo.com
			status = sendMimeMail(JIFFGO_FROM,rideRequest.getRequester().getEmail(),subject,text);//mail to user.
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    private boolean processRideResponse(RideResponse rideResponse){
		User user = rideResponse.getRideRequest().getRequester();
		if(sendRideResponseMail(rideResponse)){//Save mailSent field to 1 
			
		}
    	
		return true;
    }
 
    public void sendSimpleMail(String from, String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		message.setBcc("raju.bhucs@gmail.com");
		mailSender.send(message);
	}
	
	public boolean sendMimeMail(String from, String to, String subject, String msg) throws MessagingException {
		JavaMailSenderImpl mimeMailSender =((JavaMailSenderImpl)(mailSender)); 
		
		MimeMessage mimeMsg = mimeMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
		helper.setTo(to);
		helper.setBcc("raju.bhucs@gmail.com");
		helper.setFrom(from);
		helper.setSubject(subject);
		helper.setText(msg,true);
		mimeMailSender.send(mimeMsg);
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean sendRideResponseMail(RideResponse rideResponse) {
		Map model = new HashMap();	          
		Ride ride = null;
		if(rideResponse.getResponseObject() != null){
			ride = (Ride)rideResponse.getResponseObject();
		}
		
		model.put("rideRequest", rideResponse.getRideRequest());
		model.put("rideResponse", ride);
		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "resources/mailTemplate.vm", "UTF-8", model);
		User user = rideResponse.getRideRequest().getRequester();
		boolean status = Boolean.FALSE;
		String subject = getSubject(ride);
		try {
			status = sendMimeMail(JIFFGO_FROM,user.getEmail(),subject,text);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
   
    
    private String getSubject(Ride ride) {
    	String subject= "Ride Scheduled";
		if(ride != null && ride.getStatus().equals("processing")){
			subject = "Ride Processing";
		}
		return subject;
	}

	@Override
    protected void finalize() throws Throwable {
    	super.finalize();
    	stop();
    }

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

}