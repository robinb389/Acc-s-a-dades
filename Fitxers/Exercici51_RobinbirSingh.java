import java.io.*;
import java.util.Scanner;

public class Exercici51_RobinbirSingh {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try (RandomAccessFile fitxer = new RandomAccessFile("paisos.dat", "rw")) {
            System.out.print("Introdueix l'índex del registre a modificar: ");
            int index = sc.nextInt();
            sc.nextLine();

            fitxer.seek(0);
            for (int i = 0; i < index; i++) {
                fitxer.readInt();
                fitxer.readUTF();
                fitxer.readInt();
                fitxer.readUTF();
            }

            long posRegistre = fitxer.getFilePointer();

            int codi = fitxer.readInt();
            String nom = fitxer.readUTF();
            int poblacio = fitxer.readInt();
            String capital = fitxer.readUTF();

            System.out.print("Quin camp vols modificar (codi, nom, poblacio, capital)? ");
            String camp = sc.nextLine().toLowerCase();

            System.out.print("Nou valor: ");
            String nou = sc.nextLine();

            fitxer.seek(posRegistre);

            if (camp.equals("codi")) {
                fitxer.writeInt(Integer.parseInt(nou));
                fitxer.writeUTF(nom);
                fitxer.writeInt(poblacio);
                fitxer.writeUTF(capital);
            } else if (camp.equals("nom")) {
                fitxer.writeInt(codi);
                fitxer.writeUTF(nou);
                fitxer.writeInt(poblacio);
                fitxer.writeUTF(capital);
            } else if (camp.equals("poblacio")) {
                fitxer.writeInt(codi);
                fitxer.writeUTF(nom);
                fitxer.writeInt(Integer.parseInt(nou));
                fitxer.writeUTF(capital);
            } else if (camp.equals("capital")) {
                fitxer.writeInt(codi);
                fitxer.writeUTF(nom);
                fitxer.writeInt(poblacio);
                fitxer.writeUTF(nou);
            }

            System.out.println("Registre modificat correctament!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
