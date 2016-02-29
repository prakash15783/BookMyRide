package bookmyride.retry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bookmyride.RequestStatus;
import bookmyride.RideRequest;

public class BackOffUtil {
	
	static final Map<String,BackOffRequestHandler> requestIdHandlerMap = new ConcurrentHashMap<String, BackOffRequestHandler>();
	
	public static BackOffRequestHandler getBackOffRequestHandler(){
		return new FailedRequestBackOffHandler(new ExponentialBackOff.Builder().build());
	}
	
	public static BackOff getExponentialBackOff(){
		return new ExponentialBackOff.Builder().build();
	}
	
	public static boolean reprocessingRequired(RideRequest rideRequest){
		if(rideRequest.getRequestStatus() == RequestStatus.RequestFailed ||
				rideRequest.getRequestStatus() == RequestStatus.RequestDriverCanceled ||
				rideRequest.getRequestStatus() == RequestStatus.RequestNoDriver
				){
			return true;
		}
		else{
			return false;
		}
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
