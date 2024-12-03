import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;


public class ClientHandler implements Runnable {
    private Socket socket;
    private static ConcurrentHashMap<String, BlockingDeque<String>> queue;

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

    public static String getQueue(String key) {
            return queue.get(key).pollFirst();
    }

    public ClientHandler(Socket socket, ConcurrentHashMap<String, BlockingDeque<String>> queue) {
        this.socket = socket;
        this.queue = queue;
    }

    static boolean flag = true;

    public void run() {
        BufferedReader input = null;
        try {
            while (true) {
                if (flag)
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream());
                System.out.println(flag);
                String[] message = input.readLine().replace("\\n", "\n").split("\n");
                String[] command = message[0].split(" ");
                String nameQueue = "";
                int bytesRead = 0;
                if (command[0].equals("receive")) {
                    flag = false;
                    while (true) {
                        output.println(getQueue(command[1]) + queue);
                        output.flush();
                    }
                } else if (command[0].equals("send")) {
                    setNameQueue(command[1]);
                } else if (command[0].equals("message")) {
                    output.println("add the element at queue: " + "masage");
                    System.out.println("done");
                    setQueue("test", "message[1]");
                    output.flush();
                } else {
                    output.println("Введите верный формат [command] [queue]");
                    output.flush();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
