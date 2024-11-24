import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен. Ожидание подключения...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("клиен подключён " + clientSocket.getInetAddress());

                new ClientThread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
