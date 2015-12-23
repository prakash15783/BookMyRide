package bookmyride

import org.codehaus.groovy.grails.commons.ApplicationHolder

class BookMyRideUtils {

	String getMailFormat(){
		def filePath = "resources/mailTemplate.txt"
		def text = ApplicationHolder.application.parentContext.getResource("classpath:$filePath").inputStream.text
		return text;
	}
}
