class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller:"BMR", action:"index")
		"/Callback"(controller:"Callback", action:"callback")
		"500"(view:'/error')
	}
}
