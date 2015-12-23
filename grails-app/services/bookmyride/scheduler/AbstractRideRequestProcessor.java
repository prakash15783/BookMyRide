package bookmyride.scheduler;

import java.sql.Timestamp;

import bookmyride.RideRequest;
import bookmyride.RideRequestLog;

public abstract class AbstractRideRequestProcessor implements RideRequestProcessor {
    
	protected RideRequestLog createRequestLog(RideRequest rideRequest){
        //create RideRequestLog object.
        RideRequestLog rideReqLog = new RideRequestLog();
        rideReqLog.setRideRequest(rideRequest);
        rideReqLog.setStartTime(new Timestamp(System.currentTimeMillis()));
        return rideReqLog;
        
    }

    protected boolean submitRequestLog(RideRequestLog rideRequestLog){
        //create RideRequestLog object.
    	
        return true;
    }
    
    
}