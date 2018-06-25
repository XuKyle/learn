package concurrency.ch5.section3;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<PCData> queue;
    private static final int SlEEP_TIME = 1000;

    public Consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Start Consumer id = " + Thread.currentThread().getId());

        Random random = new Random();

        try {
            while (true) {
                PCData data = queue.take();
                if (null != data) {
                    int result = data.getPCData() * data.getPCData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}", data.getPCData(), data.getPCData(), result));
                    Thread.sleep(random.nextInt(SlEEP_TIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }
}
