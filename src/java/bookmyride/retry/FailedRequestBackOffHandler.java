package bookmyride.retry;

import bookmyride.BookMyRideConstants;
import bookmyride.CommonDataStore;
import bookmyride.RequestQueue;
import bookmyride.RideRequest;

public class FailedRequestBackOffHandler implements BackOffRequestHandler {
	private static final RequestQueue requestQueue = (RequestQueue)CommonDataStore.getDataStore(BookMyRideConstants.REQUEST_QUEUE);
	
	private BackOff backoff;
	
	public FailedRequestBackOffHandler(BackOff backoff){
		this.backoff = backoff;
	}
	
	public void execute(RideRequest rideRequest){
		long retryInterval = backoff.nextBackOffMillis();
		if(retryInterval == BackOff.STOP){
			//Dont put in queue.
			return;
		}
		else{
			//Put it in RequestQueue
			if (requestQueue != null && rideRequest !=null ) {
				try {
					Thread.sleep(retryInterval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				requestQueue.enqueueRideRequest(rideRequest);
			}
		}
		
	}

	public boolean moreRetry(){
		return backoff.nextBackOffMillis() == BackOff.STOP ? true: false;
	}
	@Override
	public BackOff getBackOff() {
		return backoff;
	}
	
	
}
