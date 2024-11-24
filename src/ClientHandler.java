import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private static BufferedReader reader;

    private static int countClients = 1;

    public static void main(String[] args) {
        int bytesRead = 0;
        String[] line;
        String[] command;
        String message;
        List<Client> sendClients = new ArrayList<>();
        try {
            try {
                reader = new BufferedReader(new InputStreamReader(System.in));

                System.out.println("Write something to send message: ");
                do {
                    line = reader.readLine().replace("\\n", "\n").split("\n");
                    command = line[0].split(" ");
                    System.out.println(line[0]);
                    if (command[0].equals("send")) {
                        sendClients.add(new Client());
                        countClients++;
                        for (Client i : sendClients) {
//                            System.out.println(i.clientSocket + " ");
                        }
                    } else if (command[0].equals("receive")) {
                        System.out.println("Приём сообщений");
                    } else if (command[0].equals("message")) {
                        bytesRead = Integer.parseInt(line[0].split(" ")[1]);
                        message = line[1];
                        if (message.length() == bytesRead) {
//                            out.write("ответ от сервера\n");
//                            out.flush();
                        } else {
                            System.out.println("Неверное количество байт");
                            continue;
                        }
                        System.out.println("Отправка сообщений");
                    }
                } while (line[0].equals("quit"));


//                String messageToClient = reader.readLine();
//                out.write(messageToClient + "\n");
//                out.flush();
//                String wordServer = in.readLine();
//                System.out.println("Server sending: " + wordServer);

            } finally {
                System.out.println("The client has been closed...");
//                clientSocket.close();
//                in.close();
//                out.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
