import java.net.Socket;


//Класс принимает клиента в отдельный поток и обрабатывает его
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final QueueHandler queue;
    private final TerminalHandler terminal;

    public ClientHandler(Socket socket, QueueHandler queue) {
        this.socket = socket;
        this.queue = queue;
        this.terminal = new TerminalHandler(socket);
    }

    public void run() {
        String nameQueue = "";
        while (terminal.isConnectClient()) {
            try {
                String input = terminal.getMessage();
                String[] message = input.replace("\\n", "\n").split("\n");
                String[] command = message[0].split(" ");
                if (command[0].equals("receive")) {
                    queue.setKeyQueue(command[1], socket);
                    terminal.sendMessage("Вы успешно присоединились к очереди " + command[1]);

                    while (terminal.isConnectClient()) {
                        terminal.sendMessage(queue.getQueue(command[1], socket));
                    }
                } else if (command[0].equals("send")) {
                    nameQueue = command[1];
                    queue.setKeyQueue(command[1], socket);
                    terminal.sendMessage("Присоединение к очереди в качестве отправителя: " + command[1]);
                } else if (command[0].equals("message")) {
                    if (message[1].length() == Integer.parseInt(command[1])) {
                        queue.setQueue(nameQueue, message[1]);
                        terminal.sendMessage("Добавленение элемента в очередь " + command[1]);
                    } else {
                        terminal.sendMessage("Неверное количество байт");
                    }
                } else {
                    terminal.sendMessage("Введите верный формат [command] [queue]",
                            "Не поддерживается данный формат сообщений");
                }
            } catch (Exception e) {
                terminal.sendMessage("Не удалось выполнить действие", e.toString());
            }
        }
    }
}
