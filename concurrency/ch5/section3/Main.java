package concurrency.ch5.section3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue<PCData> queue = new LinkedBlockingQueue<>(10);

        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);


        Consumer consumer1 = new Consumer(queue);
        Consumer consumer2 = new Consumer(queue);
        Consumer consumer3 = new Consumer(queue);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(producer1);
        executorService.submit(producer2);
        executorService.submit(producer3);
        executorService.submit(consumer1);
        executorService.submit(consumer2);
        executorService.submit(consumer3);

        Thread.sleep(10 * 1000);

        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(3000);

        executorService.shutdown();
    }
}
