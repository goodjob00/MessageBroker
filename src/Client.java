import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Client {

    public Client() {
        try (Socket socket = new Socket("localhost", 12345)) {
            System.out.println("Подключение к серверу...");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            String userInput;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
