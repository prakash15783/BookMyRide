package bookmyride.retry;

public interface BackOff {
	final static long STOP = -1; //Indicates that no more retries should be made for use in nextBackOffMillis()
	
	long nextBackOffMillis();
	
	final static BackOff STOP_BACKOFF = new BackOff(){
		@Override
		public long nextBackOffMillis() {
			// Fixed back-off policy that always returns #STOP for nextBackOffMillis(), meaning that the operation should not be retried.
			return STOP;
		} 
		};
		
	final static BackOff ZERO_BACKOFF = new BackOff(){
		@Override
		public long nextBackOffMillis() {
			// Fixed back-off policy whose back-off time is always zero, meaning that the operation is retried immediately without waiting.
			return 0;
		} };
}
