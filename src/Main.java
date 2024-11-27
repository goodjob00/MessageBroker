import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String user;
        String nameQueue = "test";
        int countQueue = 1;
        int countMessage = 1;
        HashMap<String, BlockingDeque> clients = new HashMap<>();
        while (!(user = scanner.nextLine()).isEmpty()) {
            if (user.equals("send")) {
                System.out.println("Создание отправителя");
                clients.put(nameQueue + countQueue, new LinkedBlockingDeque());
                new ClientThread(new Socket(), clients);
                countQueue++;
            } else if (user.equals("receive")) {
                System.out.println("Создание получателя");
            } else if (user.equals("message")) {
                clients.get("test1").put("сообщение " + countMessage);
                countMessage++;
                System.out.println("Вставка в очередь сообщений");
                System.out.println(clients.get("test1"));
            }
        }

        System.out.println(clients);
    }
}