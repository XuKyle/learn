package concurrency.ch2;

public class StopThreadSafe {

    public static User user = new User();

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();

        while (true) {
            ChangeObjectThread thread = new ChangeObjectThread();
            thread.start();
            Thread.sleep(150);
            thread.stopFlag = true;
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (user) {
                    if (user.getId() != Integer.parseInt(user.getName())) {
                        System.out.println(user.toString());
                    }
                }
                Thread.yield();
            }
        }
    }

    public static class ChangeObjectThread extends Thread {
        volatile boolean stopFlag = false;

        public void stopMe() {
            stopFlag = true;
        }

        @Override
        public void run() {
            while (true) {
                if (stopFlag) {
                    System.out.println("exit by stop flag!");
                    break;
                }

                synchronized (user) {
                    int v = (int) (System.currentTimeMillis() / 1000);
                    user.setId(v);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    user.setName(String.valueOf(v));
                }

                Thread.yield();
            }
        }
    }


    public static class User {
        private int id;
        private String name;

        public User() {
            id = 0;
            name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
