package bookmyride

enum RequestStatus {
	
	RequestScheduled("Scheduled"),
	RequestUserCancelled("Cancelled"),
	RequestFailed("Failed"),
	//ride status as per uber
	RequestProcessing("processing"),
	RequestNoDriver("no_drivers_available"),
	RequestAccepted("accepted"),
	RequestArriving("arriving"),
	RequestInProgress("in_progress"),
	RequestDriverCanceled("driver_canceled"),
	RequestRiderCanceled("rider_canceled"),
	RequestCompleted("completed"),

	
	String name
	RequestStatus(String name) { this.name = name }
	
	RequestStatus getRequestStatus(String req){
		
	}

}

/*
 * processing	The Request is matching to the most efficient available driver.
no_drivers_available	The Request was unfulfilled because no drivers were available.
accepted	The Request has been accepted by a driver and is "en route" to the start location (i.e. start_latitude and start_longitude).
arriving	The driver has arrived or will be shortly.
in_progress	The Request is "en route" from the start location to the end location.
driver_canceled	The Request has been canceled by the driver.
rider_canceled	The Request canceled by rider.
completed	Request has been completed by the driver.
 */