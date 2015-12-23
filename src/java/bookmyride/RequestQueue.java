package bookmyride;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class RequestQueue {
	private LinkedBlockingQueue<RideRequest> queue = new LinkedBlockingQueue<RideRequest>();

    public void enqueueRideRequest(RideRequest rideRequest){
        queue.add(rideRequest);
        System.out.println("Current Q: " +queue);
    }
    public void enqueueRideRequests(List<RideRequest> rideReqList){
        queue.addAll(rideReqList);
        System.out.println("Current Q: " +queue.size());
        System.out.println("Current Q List: " +rideReqList);
    }

    public RideRequest takeRideRequest() throws InterruptedException {
    	RideRequest r =  queue.take();
        System.out.println("Current Q takeRideRequest: " +queue.size());
        return r;
    }
    
    public int getSize(){
    	return queue.size();
    }
}
