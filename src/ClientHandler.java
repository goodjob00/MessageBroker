import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.BlockingDeque;


public class ClientHandler implements Runnable {
    private Socket socket;
    private QueueHandler queue;

    public ClientHandler(Socket socket, QueueHandler queue) {
        this.socket = socket;
        this.queue = queue;
    }

    public void run() {
        try {
            String nameQueue = "";
            while (true) {
                System.out.println(queue.isConnectClient(socket));
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                String[] message = input.readLine().replace("\\n", "\n").split("\n");
                String[] command = message[0].split(" ");
                if (command[0].equals("##")) {
                    output.println("Завершение работы...");
                    output.flush();
                    return;
                }
                if (command[0].equals("receive")) {
                    queue.setNameQueue(command[1], socket);
                    output.println("Вы успешно присоединились к очереди " + command[1]);
                    output.flush();

                    while (true) {
                        System.out.println("отправка сообщения receive");
                        output.println(queue.getQueue(command[1]));
                        output.flush();

                        if (output.checkError()) {
                            throw new IOException();
                        }
                        System.out.println("после flush");
                    }
                } else if (command[0].equals("send")) {
                    nameQueue = command[1];
                    output.println("Присоединение к очереди в качестве отправителя: " + command[1]);
                    output.flush();
                } else if (command[0].equals("message")) {
                    if (message[1].length() == Integer.parseInt(command[1])) {
                        queue.setQueue(nameQueue, message[1]);

                        output.println("Add the element to queue: " + command[1]);
                        output.flush();
                    } else {
                        output.println("Неверное количество байт");
                        output.flush();
                    }
                } else if (command[0].equals("get")) {
                    System.out.println(queue);
                    for (Map.Entry<String, BlockingDeque<String>> i : queue.queue.entrySet()) {
                        System.out.println(queue.queue.get(i));
                    }
                    output.println("command accepted");
                    output.flush();
                } else {
                    output.println("Введите верный формат [command] [queue]");
                    output.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("test");
            e.printStackTrace();
        }
    }
}
