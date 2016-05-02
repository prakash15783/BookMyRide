class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller:"BMR", action:"index")
		"/login"(controller:"BMR", action:"login")
		"/logout"(controller:"BMR", action:"logout")
		"/request"(controller:"BMR", action:"request")
		"/confirm"(controller:"BMR", action:"confirm")
		"/products"(controller:"BMR", action:"getProducts")
		"/product"(controller:"BMR", action:"getProduct")
		"/submitrequest"(controller:"BMR", action:"submitRequest")
		"/cancelrequest"(controller:"BMR", action:"cancelRequest")
		"/queue"(controller:"BMR", action:"queue")
		"/history"(controller:"BMR", action:"history")
		"/faq"(controller:"BMR", action:"faq")
		"/notice"(controller:"BMR", action:"notice")
		"/payment"(controller:"BMR", action:"payment")
		"/paymentMethods"(controller:"BMR", action:"getPaymentMethods")
		"/paymentMethod"(controller:"BMR", action:"getPaymentMethod")
		"/callback"(controller:"Callback", action:"callback")
		"500"(view:'/error')
		"/contactus"(controller:"BMR", action:"contactus")
		"/submitcontactus"(controller:"BMR", action:"submitcontactus")
		"/thankyou"(controller:"BMR",action:"thankyou")
		"/timezone"(controller:"BMR",action:"getTimeZoneIdForPickupLocation")
		
		"/admin/history"(controller:"Admin", action:"history")
		"/admin/rideRequestLog"(controller:"Admin", action:"rideRequestLog")
		"/admin/webhookLog"(controller:"Admin", action:"webhookLog")
		"/admin/messages"(controller:"Admin", action:"messages")
		"/admin/users"(controller:"Admin", action:"users")
		"/webhook"(controller:"Callback", action:"webhook")
		"/surgecallback"(controller:"Callback", action:"surgecallback")
	}
}
