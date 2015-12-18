package bookmyride

import org.codehaus.groovy.grails.orm.hibernate.cfg.IdentityEnumType

class RideRequest {
	// User who is requesting for a ride
	User requester;
	// Pickup location
	Float startLatitude;
	Float startLongitude;
	// Drop off location
	Float endLatitude;
	Float endLongitude;
	// Whether surge needed
	String surgeConfirmationId;
	// Product/cab to request
	String productId
	// date and time of pickup
	Date requestDate;
	// Request status (Scheduled, Completed, Cancelled)
	RequestStatus requestStatus;

    static constraints = {
		requester(nullable: false)
		startLatitude(nullable: false)
		startLongitude(nullable: false)
		endLatitude(nullable: false)
		surgeConfirmationId(nullable: true)
		productId(nullable:false)
		requestStatus(type: IdentityEnumType)
		requestDate(nullable:false)
    }
	
	// A RideRequest belongs to a User
	static belongsTo = [requester:User]
}
