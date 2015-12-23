package bookmyride

enum RequestStatus {
	
	RequestScheduled("Scheduled"),
	RequestCompleted("Completed"),
	RequestCancelled("Cancelled")
	
	String name
	RequestStatus(String name) { this.name = name }

}
