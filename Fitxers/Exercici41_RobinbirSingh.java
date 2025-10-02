import java.io.*;
import java.util.Scanner;

public class Exercici41_RobinbirSingh {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Usuari: ");
        String userName = sc.nextLine();

        System.out.print("Contrasenya: ");
        String password = sc.nextLine();

        String fileName = userName + ".usr";
        File fitxer = new File(fileName);

        if (fitxer.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(fitxer))) {
                String nomGuardat = dis.readUTF();
                String passGuardada = dis.readUTF();

                if (passGuardada.equals(password)) {
                    System.out.println("Accés correcte al sistema");
                } else {
                    System.out.println("Accés no concedit: La contrasenya no és correcta");
                }
            } catch (IOException e) {
                System.out.println("Error llegint usuari: " + e.getMessage());
            }
        } else {
            System.out.println("No se ha trobat el usuari, vols registrar-te? (s/n)");
            String resposta = sc.nextLine();

            if (resposta.equalsIgnoreCase("s")) {
                try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fitxer))) {
                    dos.writeUTF(userName);
                    dos.writeUTF(password);
                    System.out.println("Usuari registrat correctament!");
                } catch (IOException e) {
                    System.out.println("Error guardant usuari: " + e.getMessage());
                }
            } else {
                System.out.println("Fins aviat!");
            }
        }

        sc.close();
    }
}