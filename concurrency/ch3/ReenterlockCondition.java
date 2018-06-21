package concurrency.ch3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterlockCondition implements Runnable {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            condition.await();
            System.out.println("Thread is going on!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReenterlockCondition reenterlockCondition = new ReenterlockCondition();
        Thread thread = new Thread(reenterlockCondition);
        thread.start();

        Thread.sleep(2000);

        lock.lock();
        System.out.println("main thread lock");
        condition.signal();
        lock.unlock();
    }
}
