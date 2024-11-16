import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        LinkedList<String> queue = new LinkedList<String>();

        queue.add("Hello world");
        queue.add("Help please");
        System.out.println(queue.pollFirst());
        System.out.println(queue.pollFirst());
    }
}