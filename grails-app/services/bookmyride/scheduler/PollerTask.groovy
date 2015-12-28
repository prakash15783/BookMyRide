package bookmyride.scheduler;

import bookmyride.RequestQueue
import bookmyride.RequestStatus
import bookmyride.RideRequest
import java.text.SimpleDateFormat

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
		Date current = new Date();
		Calendar c1 = Calendar.getInstance();
		c1.setTime(current);
		
		Calendar c2 = Calendar.getInstance();
		
		for(RideRequest r in rideRequstList)
		{
			c2.setTime(r.getRequestDate())
			if( (c1.get(Calendar.YEAR))==(c2.get(Calendar.YEAR)) && 
				(c1.get(Calendar.MONTH))==(c2.get(Calendar.MONTH)) &&
				(c1.get(Calendar.DAY_OF_MONTH))==(c2.get(Calendar.DAY_OF_MONTH)) &&
				(c1.get(Calendar.HOUR_OF_DAY))==(c2.get(Calendar.HOUR_OF_DAY)) &&
				(c1.get(Calendar.MINUTE))==(c2.get(Calendar.MINUTE))
				) {
				continue;
			}
			else
			{
				rideRequstList.remove(r);
			}
			
		}
		
		return rideRequstList;
	}
}