package concurrency.ch5.section5;

public class RealData implements Data {
    protected final String result;

    public RealData(String para) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = stringBuilder.toString();
    }

    public String getResult() {
        return result;
    }
}
