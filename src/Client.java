import java.io.*;
import java.net.Socket;


public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
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
                            output.println(200);
                            output.flush();
                        } else {
                            System.out.println("Ответ от сервера " + serverResponse);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Соединение с сервером прервано.");
                }
            });

            serverListener.start();

            while (true) {
                userInput = reader.readLine();
                output.println(userInput);
                if (userInput.equals("##")) {
                    input.close();
                    output.close();
                    reader.close();
                    socket.close();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
