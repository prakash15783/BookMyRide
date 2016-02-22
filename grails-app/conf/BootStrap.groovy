import bookmyride.ServiceLauncher

class BootStrap {

    def init = { servletContext ->
		System.setProperty("http.proxyHost", "www-proxy.us.oracle.com");
		System.setProperty("http.proxyPort", "80");
		
		ServiceLauncher.LaunchServices();
    }
    def destroy = {
    }
}
