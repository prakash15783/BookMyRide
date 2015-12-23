import bookmyride.ServiceLauncher

class BootStrap {

    def init = { servletContext ->
		
		ServiceLauncher.LaunchServices();
    }
    def destroy = {
    }
}
