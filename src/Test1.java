import java.lang.ref.Cleaner;
import java.net.Socket;

public class Test1 {
    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            Client socket = new Client();
            socket.connectToServer();
        }
    }
}
