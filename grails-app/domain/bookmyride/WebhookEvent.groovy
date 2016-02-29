package bookmyride

class WebhookEvent {

	String eventId;

	int eventTime;
	String eventType;
	String resourceHref;
	
    static constraints = {
		id()
		eventId()
		eventTime()
		eventType()
		resourceHref()
    }

	public Date getEventDate(){
		return new Date(eventTime);
	}

}

/*

{
	"event_id": "3a3f3da4-14ac-4056-bbf2-d0b9cdcb0777",
	"event_time": 1427343990,
	"event_type": "requests.status_changed",
	"meta": {
		"resource_id": "2a2f3da4",
		"resource_type": "request",
		"status": "in_progress"
	},
	"resource_href": "https://api.uber.com/v1/requests/2a2f3da4"
	
	EventTime, EventType, ResourceType, Status 
}
*/
