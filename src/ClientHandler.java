import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;


public class ClientHandler implements Runnable {

    private Socket socket;
    private ConcurrentHashMap<String, BlockingDeque<String>> queue;

    static {

    }

    public synchronized void setNameQueue(String key) {

        queue.computeIfAbsent(key, k -> new LinkedBlockingDeque<>());
        System.out.println("Добавление в очередь ключа " + key);
    }

    public void setQueue(String key, String message) {
        BlockingDeque<String> deque = queue.get(key);
        try {
            deque.put(message);
            System.out.println("Элемент добавлен в очередь по ключу " + key + ": " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Ошибка при добавлении элемента в очередь: " + e.getMessage());
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

    public ClientHandler(Socket socket, ConcurrentHashMap<String, BlockingDeque<String>> queue) {
        this.socket = socket;
        this.queue = queue;
    }

    public void run() {
        BufferedReader input = null;
        try {
            String nameQueue = "";
            while (true) {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                String[] message = input.readLine().replace("\\n", "\n").split("\n");
                String[] command = message[0].split(" ");

                if (command[0].equals("receive")) {
                    if (queue.get(command[1]) == null) {
                        output.println("Очереди с именем " + command[1] + " не существует!!!");
                        output.flush();
                    } else {
                        output.println(true);
                        output.flush();
                        output.println("Вы успешно присоединились к очереди " + command[1]);
                        output.flush();

                        while (true) {
                            output.println(getQueue(command[1]));
                            output.flush();
                        }
                    }
                } else if (command[0].equals("send")) {
                    nameQueue = command[1];
                    setNameQueue(command[1]);

                    output.println("Добавлена очередь с именем " + command[1]);
                    output.flush();
                } else if (command[0].equals("message")) {
                    if (message[1].length() == Integer.parseInt(command[1])) {
                        setQueue(nameQueue, message[1]);

                        output.println("Add the element to queue: " + command[1]);
                        output.flush();
                    } else {
                        output.println("Неверное количество байт");
                        output.flush();
                    }
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
