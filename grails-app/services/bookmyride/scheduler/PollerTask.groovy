package bookmyride.scheduler;

import bookmyride.RequestQueue
import bookmyride.RequestStatus
import bookmyride.RideRequest

class PollerTask implements IPollerTask{
	protected RequestQueue requestQueue;
	
	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public PollerTask(RequestQueue requestQueue){
		this.requestQueue = requestQueue;
	}
	
	public PollerTask(){}
	
	@Override
	public void run() {
		try {
			//get rideRequest from database
			List<RideRequest> rideRequestList = getRideRequestList();

			//Put it in RequestQueue
			if (requestQueue != null) {
				if(rideRequestList != null && rideRequestList.size()>0)
					requestQueue.enqueueRideRequests(rideRequestList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private List<RideRequest> getRideRequestList() {
		List<RideRequest> rideRequstList = RideRequest.findAllByRequestStatus(RequestStatus.RequestScheduled);
		return rideRequstList;
	}
}