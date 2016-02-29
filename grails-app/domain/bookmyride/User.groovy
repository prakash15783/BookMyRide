package bookmyride

class User {

	String firstName;
	String lastName;
	String email;
	String picture;
	String promoCode;
	String uuid;
	String accessToken;
	String refreshToken;
	long tokenExpiry;
	String userSessionId;
	int ridesInYear;
	boolean paymentDone;
	boolean admin=false;

	// Constraints of a user
	static constraints = {
		firstName(nullable:false, blank:false)
		lastName(nullable:false, blank:false)
		email(nullable:false, blank:false)
		picture(nullable:true)
		promoCode(nullable:true)
		uuid(nullable:false, blank:false)
		accessToken(nullable:false, blank:false, maxSize: 20000)
		refreshToken(nullable:false, blank:false, maxSize: 20000)
		tokenExpiry(blank:false)
		userSessionId(nullable:false)
		ridesInYear()
		paymentDone()
		admin()
		id()
	}

	// A User can have many ride request
	static hasMany = [riderequests : RideRequest]

	public String getName(){
		return this.firstName +" "+this.lastName;
	}
	
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", picture=" + picture + ", promoCode="
				+ promoCode + ", uuid=" + uuid + ", accessToken=" + accessToken
				+ ", refreshToken=" + refreshToken + ", tokenExpiry="
				+ tokenExpiry + "]";
	}

	
}
