package bookmyride

import java.sql.Timestamp

import org.codehaus.groovy.grails.orm.hibernate.cfg.IdentityEnumType

class RideRequest {

	String requestId;
	// User who is requesting for a ride
	User requester;
	// Pickup location
	Float startLatitude;
	Float startLongitude;

	String startAddress;

	// Drop off location
	Float endLatitude;
	Float endLongitude;

	String endAddress;

	// Whether surge needed
	String surgeConfirmationId;
	// Product/cab to request
	String productId
	// date and time of pickup
	Date requestDate;
	// Request status (Scheduled, Completed, Cancelled)
	RequestStatus requestStatus;
	
	String uberRequestId; //Request Id generated in database
	
	Timestamp createdTimestamp;
	Timestamp updatedTimestamp;
	
	static constraints = {
		id()
		requester(nullable: false)
		startLatitude(nullable: false)
		startLongitude(nullable: false)
		startAddress(nullable:false)
		endLatitude(nullable: false)
		endLongitude(nullable: false)
		endAddress(nullable: false)
		surgeConfirmationId(nullable: true)
		productId(nullable:false)
		requestStatus(type: IdentityEnumType)
		requestDate(nullable:false)
		requestId(nullable:false)
		uberRequestId(nullable:true) 
		createdTimestamp(nullable:true)
		updatedTimestamp(nullable:true) 
	}

	// A RideRequest belongs to a User
	static belongsTo = [requester:User]

	static mapping = {
		requester lazy: false
	}

	@Override
	public String toString() {
		return "RideRequest [requestId=" + requestId + ", requester="
				+ requester + ", startLatitude=" + startLatitude
				+ ", startLongitude=" + startLongitude + ", startAddress="
				+ startAddress + ", endLatitude=" + endLatitude
				+ ", endLongitude=" + endLongitude + ", endAddress="
				+ endAddress + ", surgeConfirmationId=" + surgeConfirmationId
				+ ", productId=" + productId + ", requestDate=" + requestDate
				+ ", requestStatus=" + requestStatus + ", uberRequestId="
				+ uberRequestId + "]";
	}
}
