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
    private PrintWriter output;

    public TerminalHandler(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println(e);
            sendMessage(messageFormating("Не удалось создать потоки", e.toString()));
        }
    }

    private String messageFormating(String message) {
        String time = LocalTime.now(Clock.systemDefaultZone()).truncatedTo(ChronoUnit.SECONDS).toString();
        return time + " " + message + " #successfully";
    }

    private String messageFormating(String message, String status) {
        String time = LocalTime.now(Clock.systemDefaultZone()).truncatedTo(ChronoUnit.SECONDS).toString();
        return time + " " + message + " #" + status;
    }

    public void sendMessage(String messageClient) {
        String message = this.messageFormating(messageClient);

        output.println(message);
        output.flush();
    }

    public void sendMessage(String messageClient, String status) {
        String message = this.messageFormating(messageClient, status);

        output.println(message);
        output.flush();
    }

    public String getMessage() {
        try {
            return input.readLine();
        } catch (IOException e) {
            System.out.println(e);
            sendMessage(messageFormating("Не удалось получить сообщение", e.toString()));
            return null;
        }
    }

    public boolean isConnectClient() {
        boolean isConnectedClient = true;
        try {
            output = new PrintWriter(socket.getOutputStream(), true);

            output.println("test");
            output.flush();

            if (output.checkError()) {
                throw new IOException("Ошибка записи в поток.");
            }
        } catch (IOException e) {
            sendMessage(messageFormating("Клиент закрыт", e.toString()));
            return false;
        }
        return isConnectedClient;
    }

    public static boolean isConnectClient(Socket socket) {
        boolean isConnectedClient = true;
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            output.println("test");
            output.flush();

            if (output.checkError()) {
                throw new IOException("Ошибка записи в поток.");
            }
        } catch (IOException e) {
            return false;
        }
        return isConnectedClient;
    }
}
