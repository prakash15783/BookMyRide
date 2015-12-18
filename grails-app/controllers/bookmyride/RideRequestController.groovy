package bookmyride

import org.springframework.dao.DataIntegrityViolationException

class RideRequestController {
    
    def show(Long id) {
        def rideRequestInstance = RideRequest.get(id)
		
		//find current user from session
		User loggedInUser = (User)session.getAttribute("current_user");
        
        [rideRequestInstance: rideRequestInstance, loggedInUser: loggedInUser]
    }
    
}
