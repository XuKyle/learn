package concurrency.ch2;

public class InterruptDemo {
    public static void main(String[] args) throws InterruptedException {
        interruptWithSomething();
    }

    static void interruptWithSomething() throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Interrupted!");
                    break;
                }
                Thread.yield();
            }
        });

        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }

    static void interruptWithNoting() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Thread.yield();
                }
            }
        };

        thread.start();
        Thread.sleep(2000);
        thread.interrupt();
    }
}
