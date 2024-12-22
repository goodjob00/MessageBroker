import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;


//Класс обработки очередей
public class QueueHandler {
    public final ConcurrentHashMap<String, BlockingDeque<String>> queue;
    public ConcurrentHashMap<String, List<Socket>> connectClients = new ConcurrentHashMap<>();

    public QueueHandler(ConcurrentHashMap<String, BlockingDeque<String>> queue) {
        this.queue = queue;
        this.removeIfAbsent();
    }

    public synchronized void setNameQueue(String key, Socket socket) {
        queue.computeIfAbsent(key, k -> new LinkedBlockingDeque<>());
        connectClients.computeIfAbsent(key, k -> new ArrayList<>()).add(socket);
        System.out.println(connectClients);
        System.out.println("Добавление в очередь ключа " + key);
    }

    public void setQueue(String key, String message) {

        BlockingDeque<String> deque = queue.get(key);
        try {
            try {
                deque.put(message);
                System.out.println("Элемент добавлен в очередь по ключу " + key + ": " + message);
            } catch (NullPointerException e) {
                System.out.println("Такой очереди больше не существует");
            }
        } catch (InterruptedException e) {
            System.out.println("Ошибка при добавлении значения к очереди");
        }
    }

    public String getQueue(String key) {
        String value = null;
        try {
            value = queue.get(key).takeFirst();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }

    //Метод проверки очереди на пустоту
    private void removeIfAbsent() {
        new Thread(() -> {
            while (true) {
                if (queue.isEmpty()) continue;
                for (String key : connectClients.keySet()) {
                    List<Socket> copyOfListSockets = new ArrayList<>(connectClients.get(key));
                    for (Socket socket : copyOfListSockets) {
                        if (!isConnectClient(socket)) connectClients.get(key).remove(socket);
                    }
                    if (connectClients.get(key).isEmpty()) {
                        connectClients.remove(key);
                        queue.remove(key);
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }).start();
    }

    public synchronized boolean isConnectClient(Socket socket) {
        return socket.isConnected();
//        try {
//            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
//
//            output.println("test");
//            output.flush();
//
//            if (output.checkError()) {
//                throw new IOException("Ошибка записи в поток.");
//            }
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
    }
}
