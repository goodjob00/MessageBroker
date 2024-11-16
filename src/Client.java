import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class Client {

    private static Socket clientSocket;
    private static BufferedReader reader;

    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        LinkedList<String> queue = new LinkedList<String>();
        int bytesRead = 0;
        String line;
        try {
            try {
                clientSocket = new Socket("localhost", 4004);
                reader = new BufferedReader(new InputStreamReader(System.in));

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.println("Write something that send message: ");

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("message ")) {
                        String[] parts = line.split(" ",3);
                        if (parts.length < 3) {
                            System.out.println("Error format messages");
                            continue;
                        }

                        int messageLength;
                        try {
                            messageLength = Integer.parseInt(parts[1]);
                        } catch (NumberFormatException e) {
                            System.out.println("Length of messages must be an INTEGER");
                            continue;
                        }

                        String message = parts[2];
                        if (message.length() != messageLength) {

                        }
                    }
                }

//                String word = reader.readLine();
                queue.add(reader.readLine());
                out.write(queue.pollFirst());
                out.flush();

                String wordServer = in.readLine();
                System.out.println("Server sending: " + wordServer);

            } finally {
                System.out.println("The client has been closed...");
                clientSocket.close();
                in.close();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
