package concurrency.ch3;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExtThreadPool {
    public static class MyTask implements Runnable {
        public String name;

        public MyTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("now processing: Thread id: " + Thread.currentThread().getId() + " Task Name= " + name);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("Prepare: " + ((MyTask) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("After: " + ((MyTask) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println(" 线程池退出 ！");
            }
        };

        for (int i = 0; i < 5; i++) {
            MyTask myTask = new MyTask("Task-" + i);
            poolExecutor.execute(myTask);
            Thread.sleep(10);
        }

        poolExecutor.shutdown();
    }
}
