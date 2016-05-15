package bookmyride

import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.orm.hibernate.cfg.IdentityEnumType

import bookmyride.retry.BackOff;
import bookmyride.retry.BackOffUtil;

class RideRequest implements Mailable{

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
	
	String paymentMethodId;
	String timeZoneId;
	
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
		paymentMethodId(nullable:true)
		timeZoneId(nullable:true)
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
				+ uberRequestId + ", createdTimestamp="+ createdTimestamp 
				+ ", updatedTimestamp="+ updatedTimestamp + ", paymentMethodId="
				+ paymentMethodId + ", timeZoneId="+ timeZoneId + "]";
	}
	
	public void reprocessRideRequest(){
		if(reprocessingRequired() && BookMyRideConstants.reprocessedFailedRequest){
			BackOffUtil.reProcessRideRequest(this);
		}else{
			removeBackOffRequestHandler();
		}
	}
	
	public void removeBackOffRequestHandler(){
		BackOffUtil.removeBackOffRequestHandler(this);
	}
	
	public boolean reprocessingRequired(){
		if(this.getRequestStatus() == RequestStatus.RequestFailed ||
				this.getRequestStatus() == RequestStatus.RequestDriverCanceled ||
				this.getRequestStatus() == RequestStatus.RequestNoDriver
				){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String getRequestDateWithTimeZone(){
		SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		isoFormat.setTimeZone(TimeZone.getTimeZone(timeZoneId));
		String formattedDate = isoFormat.format(requestDate);
		formattedDate = formattedDate + " " + timeZoneId;
		return formattedDate;
	}
	
	public String getStatus(){
		return requestStatus.getName();
	}
	
}
