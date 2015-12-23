package bookmyride.scheduler.uber.mock;

import bookmyride.RideRequest
import bookmyride.User
import bookmyride.scheduler.PollerTask

class MockPollerTask extends PollerTask{
	@Override
	public void run() {
		try {
			println("Inside PollerTask run...")
			//get rideRequest from database
			List<RideRequest> rideRequestList = getRideRequestList();

			//Put it in RequestQueue
			if (requestQueue != null) {
				if(rideRequestList != null && rideRequestList.size()>0)
					requestQueue.enqueueRideRequests(rideRequestList);
			}
		} catch (Exception e) {
			println(e.getMessage());
			e.printStackTrace();
		}


	}
	private List<RideRequest> getRideRequestList() {
		List<RideRequest> rideRequstList = new ArrayList<RideRequest>();
		RideRequest rideRequest = getMockObject("0");
		rideRequstList.add(rideRequest);

		rideRequest.setRequestId("1"+rideRequest.getRequestId())
		rideRequstList.add(rideRequest);

		rideRequest.setRequestId("2"+rideRequest.getRequestId())
		rideRequstList.add(rideRequest);

		rideRequest.setRequestId("3"+rideRequest.getRequestId())
		rideRequstList.add(rideRequest);

		return rideRequstList;
	}

	private RideRequest getMockObject(String id){
		RideRequest r = new RideRequest();
		r.setRequestDate(new Date());
		r.setStartLatitude(12);
		r.setStartLongitude(12);
		r.setEndLatitude(77);
		r.setEndLongitude(77);
		r.setProductId("product_"+id);
		r.setRequestId(id);
		User user = new User();
		user.setEmail("raju.bhucs@gmail.com");
		r.setRequester(user);

		return r;
	}
}