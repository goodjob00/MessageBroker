import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            List<String> logs = new ArrayList<>();
            System.out.println("Подключение к серверу...");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;

            // Отдельный поток, чтобы слушать сервер
            Thread serverListener = new Thread(() -> {
                try {
                    while (true) {
                        String serverResponse = input.readLine();
                        if (serverResponse.equals("test")) {
                            // ошибка была в том, что поток отправляет 200, а ClientHandler его принимает
//                            output.println(200);
//                            output.flush();
                        } else {
                            logs.add(serverResponse);
                            System.out.println(serverResponse);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Соединение с сервером прервано.");
                }
            });

            serverListener.start();

            while (true) {
                userInput = reader.readLine();
                if (userInput.equals("check")) {
                    System.out.println("Вывод логов:");
                    for (String i : logs) {
                        System.out.println(i);
                    }
                    continue;
                }
                output.println(userInput);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
