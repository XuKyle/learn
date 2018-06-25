package concurrency.ch5.disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class Producer {
    private final RingBuffer<PCData> ringBuffer;

    public Producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void pushData(ByteBuffer byteBuffer) {
        long next = ringBuffer.next();
        try {
            PCData pcData = ringBuffer.get(next);
            pcData.setValue(byteBuffer.getLong(0));
        } finally {
            ringBuffer.publish(next);
        }
    }
}