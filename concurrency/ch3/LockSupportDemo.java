package concurrency.ch3;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static Object u = new Object();

    static ChangeObjectThread t1 = new ChangeObjectThread("T1");
    static ChangeObjectThread t2 = new ChangeObjectThread("T2");

    public static class ChangeObjectThread extends Thread {
        public ChangeObjectThread(String name) {
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("in " + getName());
                LockSupport.park();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        Thread.sleep(1000);
        t2.start();

        LockSupport.unpark(t1);
        LockSupport.unpark(t2);

        t1.join();
        t2.join();
    }
}
