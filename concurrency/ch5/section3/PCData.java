package concurrency.ch5.section3;

public class PCData {
    private final int intData;

    public PCData(int d) {
        intData = d;
    }

    public PCData(String d) {
        intData = Integer.valueOf(d);
    }

    public int getPCData() {
        return intData;
    }

    @Override
    public String toString() {
        return "PCData{" +
                "intData=" + intData +
                '}';
    }
}
