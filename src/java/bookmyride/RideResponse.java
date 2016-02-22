package bookmyride;

public class RideResponse implements Mailable {

	private Object responseObject;
	private RideRequest rideRequest;
	
	public RideResponse(RideRequest rideRequest,Object resp){
		this.setRideRequest(rideRequest);
		this.responseObject = resp;
	}
	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object response) {
		this.responseObject = response;
	}
	public RideRequest getRideRequest() {
		return rideRequest;
	}
	public void setRideRequest(RideRequest rideRequest) {
		this.rideRequest = rideRequest;
	}
	
}
