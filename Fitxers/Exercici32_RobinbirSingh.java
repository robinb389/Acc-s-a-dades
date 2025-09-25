import java.io.*;
import java.util.Scanner;

public class Exercici32_RobinbirSingh {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a code to search: ");
        int userCode = sc.nextInt();

        boolean found = false;

        try (DataInputStream dis = new DataInputStream(new FileInputStream("secret.bin"))) {
            while (true) {
                try {
                    int code = dis.readInt();
                    StringBuilder secret = new StringBuilder();
                    for (int i = 0; i < 3; i++) {
                        secret.append(dis.readChar());
                    }
                    if (code == userCode) {
                        System.out.println("Secret: " + secret.toString().toUpperCase());
                        found = true;
                        break;
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        if (!found) {
            System.out.println("Code not found");
        }
    }
}
