import bookmyride.ApplicationContextHolder

// Place your Spring DSL code here
beans = {
	applicationContextHolder(ApplicationContextHolder) { bean ->
		bean.factoryMethod = 'getInstance'
	 }
}