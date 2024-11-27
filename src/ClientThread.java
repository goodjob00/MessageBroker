import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private HashMap queue;

    public ClientThread(Socket socket, HashMap queue) {
        this.clientSocket = socket;
        this.queue = queue;
    }



    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String clientMessage;
            System.out.println("Socket in thread: " + clientSocket);
            while ((clientMessage = input.readLine()) != null) {
                System.out.println("Получено от клиента: " + clientMessage);
                // Ответ сервером
                System.out.print("Введите ответ для клиента: ");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String serverMessage = reader.readLine();
                output.write(serverMessage);
                output.newLine(); // Добавляем перевод строки, чтобы клиент знал, что сообщение окончено
                output.flush();   // Сбрасываем буфер
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap getQueue() {
        return queue;
    }
}
