package bookmyride.scheduler;

import bookmyride.RideRequest;

public interface RideRequestProcessor {
    public boolean processRideRequest(RideRequest rideRequest);
}