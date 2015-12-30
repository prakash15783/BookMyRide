package bookmyride;


import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import bookmyride.RideResponse;
import bookmyride.User;

import com.uber.sdk.rides.client.model.Ride;

public class MailHepler {
	
	private MailSender mailSender;
	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendSimpleMail(String from, String to, String subject, String msg) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(msg);
		mailSender.send(message);
	}
	
	public boolean sendMimeMail(String from, String to, String subject, String msg) throws MessagingException {
		JavaMailSenderImpl mimeMailSender =((JavaMailSenderImpl)(mailSender)); 
		
		MimeMessage mimeMsg = mimeMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
		helper.setTo(to);
		helper.setFrom(from);
		helper.setSubject(subject);
		helper.setText(msg,true);
		
		
		mimeMailSender.send(mimeMsg);
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean sendRideResponseMail(RideResponse rideResponse) {
		Map model = new HashMap();	             
		model.put("rideRequest", rideResponse.getRideRequest());
		model.put("rideResponse", (Ride)rideResponse.getResponseObject());
		String text = "<h1>hello</h1>";//VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "resources/mailTemplate.vm", "UTF-8", model);
		User user = rideResponse.getRideRequest().getRequester();
		boolean status = Boolean.FALSE;
		try {
			status = sendMimeMail("noreply@bookmyride.com",user.getEmail(),"Booking Confirmation",text);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}
