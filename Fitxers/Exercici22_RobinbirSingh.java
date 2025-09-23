import java.io.*;
import java.util.Scanner;

public class Exercici22_RobinbirSingh {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {

            FileOutputStream fos = new FileOutputStream("usertext.txt");

            String text;
            System.out.println("Escriu cadenes de text (escriu 'quit' per acabar):");

            while (true) {
                text = sc.nextLine();
                if (text.equals("quit")) {
                    break;
                }

                fos.write(text.getBytes());
                fos.write('\n');
            }

            fos.close();

            FileInputStream fis = new FileInputStream("usertext.txt");

            System.out.println("\n-- Contingut del fitxer --");
            int c;
            StringBuilder linia = new StringBuilder();

            while ((c = fis.read()) != -1) {
                if (c == '\n') {
                    System.out.println(linia.toString());
                    linia.setLength(0); // buida el buffer
                } else {
                    linia.append((char) c);
                }
            }

            if (linia.length() > 0) {
                System.out.println(linia.toString());
            }

            fis.close();

        } catch (IOException e) {
            System.out.println("Error d'entrada/sortida: " + e.getMessage());
        }

        sc.close();
    }
}
