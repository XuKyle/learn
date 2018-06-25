package concurrency.ch5.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        PCDataFactory dataFactory = new PCDataFactory();
        int bufferSize = 1024;

        Disruptor<PCData> disruptor = new Disruptor<>(dataFactory, bufferSize, executorService, ProducerType.MULTI, new BlockingWaitStrategy());

        disruptor.handleEventsWithWorkerPool(new Consumer(), new Consumer(), new Consumer(), new Consumer());
        disruptor.start();

        RingBuffer<PCData> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (int i = 0; true; i++) {
            byteBuffer.putLong(0, i);
            producer.pushData(byteBuffer);
            Thread.sleep(100);
            System.out.println("add data:" + i);
        }
    }
}
