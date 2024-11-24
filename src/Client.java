import java.io.*;
import java.net.Socket;

public class Client {

    public void connectToServer() {
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Подключение к серверу...");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;
//            while (true) {
//                System.out.print("Введите сообщение для сервера (или 'exit' для выхода): ");
//                userInput = reader.readLine();
//                if ("exit".equalsIgnoreCase(userInput)) {
//                    break; // Выход из цикла если пользователь ввел "exit"
//                }
//                output.write(userInput);
//                output.newLine(); // Добавляем перевод строки, чтобы сервер знал, что сообщение окончено
//                output.flush(); // Сбрасываем буфер
//
//                String serverResponse = input.readLine(); // получение ответа от сервера
//                System.out.println("Ответ от сервера: " + serverResponse);
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
