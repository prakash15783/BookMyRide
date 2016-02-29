package bookmyride.retry;

public class ExponentialBackOff implements BackOff {

//	The default initial interval value in milliseconds (0.5 seconds).
	static int	DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
//	The default maximum elapsed time in milliseconds (15 minutes).
	static int	DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;

	private int initalInterval;
	private int maxElapsedTime;
	private long elapsedTime = 0;
	private int retryCount = 0;
	private boolean retry = true;
	
	private ExponentialBackOff(int initInterval, int maxElapsedTime) {
		this.initalInterval = initInterval;
		this.maxElapsedTime = maxElapsedTime;
	}
	
	@Override
	public long nextBackOffMillis() {
		long waitInterval = STOP;
		if(retry){
			waitInterval = (long) (Math.pow(2, retryCount++) * initalInterval);

			elapsedTime += waitInterval;
			if(elapsedTime > maxElapsedTime){
				waitInterval = STOP;
				retry = false;
			}
		}
		return waitInterval;
	}
	
	public static class Builder {
		private int initInvertal = DEFAULT_INITIAL_INTERVAL_MILLIS;
		private int maxElapsedTime = DEFAULT_MAX_ELAPSED_TIME_MILLIS ; 
		
		public Builder setInitialInterval(int initInterval){
			this.initInvertal = initInterval;
			return this;
		}
		
		public Builder setMaxElapsedTime(int maxElapsedTime){
			this.maxElapsedTime = maxElapsedTime;
			return this;
		}
		
		public ExponentialBackOff build(){
			return new ExponentialBackOff(initInvertal,maxElapsedTime);
		}
		
	}
}
