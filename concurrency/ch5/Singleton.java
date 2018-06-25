package concurrency.ch5;

public class Singleton {

    public static int STATUS = 1;

    private Singleton() {
        System.out.println("Single is created!");
    }

    private static Singleton singleton = new Singleton();

    public static Singleton getInstance() {
        return singleton;
    }


    public static void main(String[] args) {
        System.out.println(Singleton.STATUS);
    }
}
