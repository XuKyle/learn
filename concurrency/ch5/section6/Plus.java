package concurrency.ch5.section6;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Plus implements Runnable {
    public static BlockingQueue<Msg> blockingQueue = new LinkedBlockingQueue<>();

    @Override
    public void run() {
        while (true) {
            try {
                Msg msg = blockingQueue.take();
                msg.j = msg.i + msg.j;
                Multiply.blockingQueue.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
