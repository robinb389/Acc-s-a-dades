import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Exercici21_RobinbirSingh {
    public static void main(String[] args) {
        int counter = 0;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter file path: ");
        String path = sc.nextLine();

        File file = new File(path);

        if (!file.exists()) {
            System.out.println("File does not exist.");
            sc.close();
            return;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            int c;
            while ((c = fis.read()) != -1) {
                if (c == 'a' || c == 'A') { 
                    counter++;
                }
            }
            System.out.println("The file contains " + counter + " 'a' characters.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        sc.close();
    }
}
