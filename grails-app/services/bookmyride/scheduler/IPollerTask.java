package bookmyride.scheduler;

import bookmyride.RequestQueue;

public interface IPollerTask extends Runnable {

	void setRequestQueue(RequestQueue requestQueue);
}
