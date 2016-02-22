package bookmyride;

import java.util.concurrent.LinkedBlockingQueue;


public class MailQueue{
    private LinkedBlockingQueue<Mailable> queue = new LinkedBlockingQueue<Mailable>();

    public void enqueueMailMessage(Mailable msg){
        queue.add(msg);
    }

    public Mailable takeMailMessage() throws InterruptedException {
        return queue.take();
    }
}