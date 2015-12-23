package bookmyride;

import java.util.concurrent.LinkedBlockingQueue;


public class MailQueue{
    private LinkedBlockingQueue<RideResponse> queue = new LinkedBlockingQueue<RideResponse>();

    public void enqueueMailMessage(RideResponse msg){
        queue.add(msg);
    }

    public RideResponse takeMailMessage() throws InterruptedException {
        return queue.take();
    }
}