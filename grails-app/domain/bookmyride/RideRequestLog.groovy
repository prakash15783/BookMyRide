package bookmyride

import java.sql.Timestamp
import org.codehaus.groovy.grails.orm.hibernate.cfg.IdentityEnumType

class RideRequestLog {
	
	RideRequest rideRequest;
	RequestStatus requestStatus;
	Timestamp startTime;
	Timestamp endTime;
	String details;
	
	static constraints = {
		id()
		rideRequest(nullable: false)
		startTime(nullable: false)
		endTime(nullable: false)
		details(nullable:false)
		requestStatus(type: IdentityEnumType)
	}
	
	
}
