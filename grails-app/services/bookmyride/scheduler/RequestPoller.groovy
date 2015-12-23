package bookmyride.scheduler;

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

import bookmyride.BookMyRideConstants
import bookmyride.CommonDataStore
import bookmyride.RequestQueue
import bookmyride.RideRequest

public class RequestPoller {

    private volatile boolean isRunning = false;
    private static final RequestQueue requestQueue = (RequestQueue)CommonDataStore.getDataStore(BookMyRideConstants.REQUEST_QUEUE);

	private ScheduledExecutorService executorService;
	private IPollerTask pollerTask;
	
	public RequestPoller(IPollerTask pollerTask){
		this.pollerTask = pollerTask;
	}
	
    public void start(){
        if(!isRunning){
            isRunning = true;
            executorService = Executors.newSingleThreadScheduledExecutor();
            run();
            System.out.println("RequestPoller Started...");
        }
    }

    public void stop(){
        if(isRunning){
            isRunning = false;
            executorService.shutdown();
            System.out.println("RequestPoller shutdown...");
        }
    }

    public void run() {
		println("Inside run...")
		if(pollerTask == null){
			throw new IllegalStateException("PollerTask cant be null");
		}
		pollerTask.setRequestQueue(requestQueue);
        executorService.scheduleAtFixedRate(pollerTask,0, 10, TimeUnit.SECONDS);
    }
	
}
