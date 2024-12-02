import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {
    public static void main(String[] args) {
        try {
            // Подключаемся к серверу по адресу localhost и порту 12345
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Подключено к серверу.");

            // Для чтения и записи данных
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

            String userInput;

            // Цикл для отправки сообщений
            while (true) {
                System.out.print("Введите сообщение (или 'exit' для выхода): ");
                userInput = consoleInput.readLine();

                if (userInput.equalsIgnoreCase("exit")) {
                    break; // Выход из цикла при команде "exit"
                }

                // Отправляем сообщение на сервер
                output.println(userInput);
                // Читаем ответ от сервера
                String serverResponse = input.readLine();
                System.out.println("Ответ от сервера: " + serverResponse);
            }

            // Закрываем ресурсы
            consoleInput.close();
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
