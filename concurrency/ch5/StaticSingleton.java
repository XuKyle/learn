package concurrency.ch5;

public class StaticSingleton {
    private static int i = 5;

    private StaticSingleton() {
        System.out.println("StaticSingleton is create");
    }

    private static class SingletonHolder {
        private static StaticSingleton instance = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.instance;
    }

    public static void main(String[] args) {
        System.out.println("StaticSingleton.i = " + StaticSingleton.i);
    }
}
