import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.Clock;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

//Класс отвечающий за общение между клиентом и сервером посредством потоков
public class TerminalHandler {
    private Socket socket;
    private BufferedReader input;
    private  PrintWriter output;
    private LocalTime currentTime;

    public TerminalHandler(Socket socket) {
        this.socket = socket;
        this.currentTime = LocalTime.now(Clock.systemDefaultZone()).truncatedTo(ChronoUnit.SECONDS);
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private String messageFormating(String message) {
        return currentTime + "| " + message + " |[code work]";
    }

    public void sendMessage(String messageClient) {
        String message = this.messageFormating(messageClient);

        output.println(message);
        output.flush();
    }

    public String getMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
