import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Exercici22_RobinbirSingh {
    public static void main(String[] args) {
        int counter = 0;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a word ('quit' to exit): ");

        File file = new File("test.txt");

        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream("test.txt")

}
