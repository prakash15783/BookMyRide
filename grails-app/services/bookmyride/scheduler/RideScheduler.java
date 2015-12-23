package bookmyride.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bookmyride.BookMyRideConstants;
import bookmyride.CommonDataStore;
import bookmyride.RequestQueue;
import bookmyride.RideRequest;
import bookmyride.scheduler.uber.UberRideRequestProcessor;

public class RideScheduler {

    private static final int THREAD_COUNT = 2;
	private RequestPoller requestPoller;
	

	private static final RequestQueue requestQueue = (RequestQueue)CommonDataStore.getDataStore(BookMyRideConstants.REQUEST_QUEUE);

    public RideScheduler(RequestPoller requestPoller) {
    	this.requestPoller = requestPoller;
	}
    
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        
    public void start() {
    	if(requestPoller != null)
    		requestPoller.start();
    	else{
    		throw new IllegalStateException("RequesterPoller cant be null");
    	}
    	
    	for(int i=0;i<THREAD_COUNT;i++){
    		System.out.println("Created processing thread : "+i);
    		executorService.submit(new SchedulerTask());
    	}
    }
    
    class SchedulerTask implements Runnable{
		public void run(){
			RideRequest rideRequest;
			System.out.println("Created UberRideRequestProcessor : ");
			RideRequestProcessor requestProcessor = new UberRideRequestProcessor();//Can be decided from some config.

			while(true){
				try {
					rideRequest = requestQueue.takeRideRequest();
					System.out.println("Ride Request Picked : "+rideRequest.getStartAddress() + " Status : "+rideRequest.getRequestStatus());
					System.out.println("Ride Request Picked : "+rideRequest);
					
					System.out.println("RideRequest [requestId=" + rideRequest.getRequestId() + ", requester="
					+ rideRequest.getRequester() + ", startLatitude=" + rideRequest.getStartLatitude()
					+ ", startLongitude=" + rideRequest.getStartLongitude() + ", startAddress="
					);
					
					System.out.println("SchedulerTask Queue Size : "+requestQueue.getSize());
					requestProcessor.processRideRequest(rideRequest);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
    
    @Override
    protected void finalize() throws Throwable {
    	// TODO Auto-generated method stub
    	super.finalize();
    	executorService.shutdown();
    }
    
}
