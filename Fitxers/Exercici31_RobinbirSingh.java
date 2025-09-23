import java.io.*;
import java.util.Random;

public class Exercici31_RobinbirSingh {
    public static void main(String[] args) {
        Random rand = new Random();

        try {
            FileOutputStream fos = new FileOutputStream("secret.bin");
            DataOutputStream dos = new DataOutputStream(fos);

            int code = rand.nextInt(3) + 1; 

            for (int i = 0; i < 10; i++) {
                
                StringBuilder secret = new StringBuilder();
                for (int j = 0; j < 3; j++) {
                    char c = (char) ('a' + rand.nextInt(26)); 
                    secret.append(c);
                }

                
                dos.writeInt(code);
                dos.writeChars(secret.toString()); 

                System.out.println(code + ":" + secret);

                
                code += rand.nextInt(3) + 1;
            }

            dos.close();
            fos.close();

        } catch (IOException e) {
            System.out.println("Error d'entrada/sortida: " + e.getMessage());
        }
    }
}
