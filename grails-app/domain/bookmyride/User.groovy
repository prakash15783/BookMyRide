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

	// Constraints of a user
	static constraints = {
		firstName(nullable:false, blank:false)
		lastName(nullable:false, blank:false)
		email(nullable:false, blank:false)
		picture(nullable:true)
		promoCode(nullable:true)
		uuid(nullable:false, blank:false)
		accessToken(nullable:false, blank:false, maxSize: 32760)
		refreshToken(nullable:false, blank:false, maxSize: 32760)
		tokenExpiry(blank:false)
		id()
	}

	// A User can have many ride request
	static hasMany = [riderequests : RideRequest]

}