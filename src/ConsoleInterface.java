import java.sql.SQLSyntaxErrorException;
import java.util.Scanner;


public class ConsoleInterface {
    private Scanner scanner;

    public ConsoleInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void runConsole() {
        int bytesRead = 0;
        String[] line;
        String[] command;
        String message;
        String nameQueue = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write something to send message: ");
        Client client;
        do {
            line = scanner.nextLine().replace("\\n", "\n").split("\n");
            command = line[0].split(" ");
            System.out.println(line[0]);
            if (command[0].equals("send")) {
                nameQueue = command[1];
                client = new Client();
                Server.setNameQueue(command[1]);
                System.out.println(Server.getQueue());
                System.out.println("Creating the client");
            } else if (command[0].equals("receive")) {
                System.out.println("Приём сообщений");
            } else if (command[0].equals("message")) {
                bytesRead = Integer.parseInt(line[0].split(" ")[1]);
                message = line[1];
                if (message.length() == bytesRead) {
                    Server.setQueue(nameQueue, message);
                    System.out.println(Server.getQueue());
                } else {
                    System.out.println("Неверное количество байт");
                    continue;
                }
                System.out.println("Отправка сообщений");
            }
        } while (!line[0].equals(""));
    }
}
