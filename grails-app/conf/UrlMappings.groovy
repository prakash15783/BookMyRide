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
		"/callback"(controller:"Callback", action:"callback")
		"500"(view:'/error')
		"/contactus"(controller:"BMR", action:"contactus")
		"/submitcontactus"(controller:"BMR", action:"submitcontactus")
		"/thankyou"(controller:"BMR",action:"thankyou")
		
		"/admin/history"(controller:"Admin", action:"history")
		"/admin/rideRequestLog"(controller:"Admin", action:"rideRequestLog")
		"/admin/webhookLog"(controller:"Admin", action:"webhookLog")
		"/webhook"(controller:"Callback", action:"webhook")
	}
}
