package bookmyride

enum RequestStatus {
	
	RequestScheduled("scheduled"),
	RequestUserCancelled("cancelled"),
	RequestFailed("failed"),
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
		if(req.equals("scheduled")){
			return RequestScheduled;
		}
		if(req.equals("cancelled")){
			return RequestUserCancelled;
		}
		if(req.equals("failed")){
			return RequestFailed;
		}
		if(req.equals("processing")){
			return RequestProcessing;
		}
		if(req.equals("no_drivers_available")){
			return RequestNoDriver;
		}
		if(req.equals("accepted")){
			return RequestAccepted;
		}
		if(req.equals("in_progress")){
			return RequestInProgress;
		}
		if(req.equals("driver_canceled")){
			return RequestDriverCanceled;
		}
		if(req.equals("rider_canceled")){
			return RequestRiderCanceled;
		}
		if(req.equals("completed")){
			return RequestCompleted;
		}
		if(req.equals("arriving")){
			return RequestArriving;
		}
		
		
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