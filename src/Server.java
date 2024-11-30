import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;


public class Server {
    public static ConcurrentHashMap<String, BlockingDeque> getQueue() {
        return queue;
    }

    private static ConcurrentHashMap<String, BlockingDeque> queue = new ConcurrentHashMap<>();

    public synchronized static void setNameQueue(String key) {
        if (!queue.keySet().equals(key)) queue.put(key, new LinkedBlockingDeque());
    }

    public static void setQueue(String key, String message) {
        synchronized (queue) {
            BlockingDeque<String> deque = queue.get(key);
            try {
                deque.put(message);
                System.out.println("Элемент добавлен в очередь по ключу " + key + ": " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
                System.out.println("Ошибка при добавлении элемента в очередь: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Сервер запущен. Ожидание подключения...");
            while (true) {
                Socket clientSocket = serverSocket.accept();

                new Thread(new ClientHandler(clientSocket)).start();
                System.out.println("клиенt подключён " + clientSocket.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
