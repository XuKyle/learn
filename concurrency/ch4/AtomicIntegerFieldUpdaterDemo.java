package concurrency.ch4;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
    public static class Candidate {
        int id;
        volatile int score;
    }

    public final static AtomicIntegerFieldUpdater<Candidate> scoreUpdater =
            AtomicIntegerFieldUpdater.newUpdater(Candidate.class, "score");

    private static AtomicInteger allScore = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Candidate candidate = new Candidate();
        Thread[] threads = new Thread[10000];

        for (int i = 0; i < 10000; i++) {
            threads[i] = new Thread(() -> {
                if (Math.random() > 0.4) {
                    scoreUpdater.incrementAndGet(candidate);
                    allScore.incrementAndGet();
                }
            });
            threads[i].start();
        }


        for (int i = 0; i < 1000; i++) {
            threads[i].join();
        }

        System.out.println("socre=" + candidate.score);
        System.out.println("allScore = " + allScore);
    }


}
