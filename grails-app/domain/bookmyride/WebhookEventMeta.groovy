package bookmyride

class WebhookEventMeta {

	String resourceId;
	String resourceType;
	String status;
	
    static constraints = {
		id();
    }
	static belongsTo = [webhookEvent:WebhookEvent]
}

/*
"resource_id": "2a2f3da4",
		"resource_type": "request",
		"status": "in_progress"
*/