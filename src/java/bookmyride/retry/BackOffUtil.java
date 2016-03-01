package bookmyride.retry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bookmyride.RideRequest;

public class BackOffUtil {
	
	static final Map<String,BackOffRequestHandler> requestIdHandlerMap = new ConcurrentHashMap<String, BackOffRequestHandler>();
	
	public static BackOffRequestHandler getBackOffRequestHandler(){
		return new FailedRequestBackOffHandler(getExponentialBackOff());
	}
	
	public static BackOff getExponentialBackOff(){
		return new ExponentialBackOff.Builder().build();
	}
	
	public static void removeBackOffRequestHandler(RideRequest rideRequest){
		requestIdHandlerMap.remove(rideRequest.getRequestId());
	}
	
	public static void reProcessRideRequest(RideRequest rideRequest){
		BackOffRequestHandler handler = requestIdHandlerMap.get(rideRequest.getRequestId()) ;
		if(handler == null){
			handler = getBackOffRequestHandler();
			requestIdHandlerMap.put(rideRequest.getRequestId(),handler);
		}
		if(handler.moreRetry()){
			handler.execute(rideRequest);
		}else{
			requestIdHandlerMap.remove(rideRequest.getRequestId());
		}
		
	}
}
