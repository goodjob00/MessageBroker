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
            boolean flag = true;
            while (true) {
                if (flag) {
                    userInput = reader.readLine();
                    output.println(userInput);
                }
                String serverResponse = input.readLine();
                if (Boolean.parseBoolean(serverResponse)) flag = false;
                if (!serverResponse.equals("null")) System.out.println("Ответ от сервера: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
