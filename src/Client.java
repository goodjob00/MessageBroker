import java.io.*;
import java.net.Socket;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Подключение к серверу...");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;

            while (true) {
                userInput = reader.readLine();

                output.println(userInput);
                String serverResponse = input.readLine();
                System.out.println("Ответ от сервера: " + serverResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
