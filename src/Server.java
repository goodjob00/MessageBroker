import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;


public class Server {
    public static volatile ConcurrentHashMap<String, BlockingDeque<String>> queue = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен. Ожидание подключения...");
            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(new ClientHandler(clientSocket, queue)).start();
                System.out.println("Клиент подключен " + clientSocket.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
