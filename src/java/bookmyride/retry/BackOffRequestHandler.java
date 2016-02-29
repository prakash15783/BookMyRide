package bookmyride.retry;

import bookmyride.RideRequest;

public interface BackOffRequestHandler {
	BackOff getBackOff();
	public void execute(RideRequest rideRequest);
	public boolean moreRetry();
}
