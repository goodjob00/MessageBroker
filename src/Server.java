import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class Server {
    public static ConcurrentHashMap<String, BlockingDeque<String>> getQueue() {
        return queue;
    }

    public static volatile ConcurrentHashMap<String, BlockingDeque<String>> queue = new ConcurrentHashMap<>();

    public static synchronized void setNameQueue(String key) {
        queue.computeIfAbsent(key, k -> new LinkedBlockingDeque<>());
    }

    public static void setQueue(String key, String message) {
        BlockingDeque<String> deque = queue.get(key);
        try {
            deque.put(message);
            System.out.println("Элемент добавлен в очередь по ключу " + key + ": " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Ошибка при добавлении элемента в очередь: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        setNameQueue("test");
        setQueue("test", "hello world");
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен. Ожидание подключения...");
            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(new ClientHandler(clientSocket, queue)).start();
                System.out.println("клиенt подключён " + clientSocket.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
