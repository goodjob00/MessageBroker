import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        if (flag) {
            System.out.println("Тру");
        }

        if (scanner.nextLine().equals("test")) {
            System.out.println("ok");
            flag = false;
        }
        if (flag) {
            System.out.println("Тру");
        }
        System.out.println(flag);
    }
}
