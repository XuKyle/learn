package concurrency.ch4;


import java.util.Random;
import java.util.concurrent.*;

public class ThreadLocalTest {
    public static final int GEN_COUNT = 1000_0000;
    public static final int THREAD_COUNT = 4;
    static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    public static Random random = new Random(123);

    public static ThreadLocal<Random> threadLocalRandom = ThreadLocal.withInitial(() -> new Random(123));

    public static class RndTask implements Callable<Long> {
        private int mode = 0;

        public RndTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom() {
            if (mode == 0) {
                return random;
            } else if (mode == 1) {
                return threadLocalRandom.get();
            } else {
                return null;
            }
        }

        @Override
        public Long call() {
            long b = System.currentTimeMillis();
            for (long i = 0; i < GEN_COUNT; i++) {
                getRandom().nextInt();
            }
            long e = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " spend   " + (e - b) + " ms!");
            return e - b;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] futures = new Future[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(new RndTask(0));
        }
        long totaltime = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            totaltime += futures[i].get();
        }
        System.out.println("多线程访问同一个Random实例:" + totaltime + "ms");

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures[i] = executorService.submit(new RndTask(1));
        }
        totaltime = 0;
        for (int i = 0; i < THREAD_COUNT; i++) {
            totaltime += futures[i].get();
        }

        System.out.println("使用ThreadLocal包装：" + totaltime + " ms!");
        executorService.shutdown();
    }
}
