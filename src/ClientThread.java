import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket clientSocket;

    public ClientThread(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String clientMessage;

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
}
