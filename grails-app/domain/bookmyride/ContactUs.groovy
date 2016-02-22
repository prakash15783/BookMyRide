package bookmyride

class ContactUs implements Mailable {
	
	String name;
	String email;
	String message;
	
	static constraints = {
		id()
		name(blank:false, nullable: false)
		email(blank:false, nullable: false)
		message(blank:false, nullable: false)
	}
	
	
}
