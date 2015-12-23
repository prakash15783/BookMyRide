package bookmyride;

import java.util.HashMap;
import java.util.Map;

public class CommonDataStore {//TODO: Maintain validity of datastore.
    private static Map<String,Object> dataStoreMap = new HashMap<String,Object>();
    static{
    	dataStoreMap.put(BookMyRideConstants.REQUEST_QUEUE, new RequestQueue());
    	dataStoreMap.put(BookMyRideConstants.MAIL_QUEUE, new MailQueue());
    }
    public static Object getDataStore(String key){
        return dataStoreMap.get(key);
    }

    public static void createDataStore(String key, Object dataStore){
        dataStoreMap.put(key, dataStore);
    }
}
