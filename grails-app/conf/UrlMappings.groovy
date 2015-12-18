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
		"/callback"(controller:"Callback", action:"callback")
		"500"(view:'/error')
	}
}
