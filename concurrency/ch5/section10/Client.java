package concurrency.ch5.section10;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter printWriter = null;
        try {
            client = new Socket();

            client.connect(new InetSocketAddress("localhost", 8000));
            printWriter = new PrintWriter(client.getOutputStream(), true);

            printWriter.write("Hello!");
            printWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) printWriter.close();
            if (client != null) client.close();
        }
    }
}
