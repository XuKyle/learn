package concurrency.ch5.section5;

public class Client {
    public Data request(final String queryStr) {
        final FutureData future = new FutureData();

        new Thread(() -> {
            RealData realData = new RealData(queryStr);
            future.setRealData(realData);
        }).start();

        return future;
    }
}
