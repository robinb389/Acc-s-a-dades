
import java.io.File;

public class Exercici_1_Versio_simple_de_ls {
    public static void main(String[] args){

        File path = new File(args[0]);

        File[] list = path.listFiles();

        for(File lists : list){

            if(lists.canRead()){
                System.out.print("r-");
            }
            if(lists.canExecute()){
                System.out.print("x-");
            }
            if(lists.canWrite()){
                System.out.print("w ");
            }

            if(lists.isDirectory()){
                System.out.println(lists.getName() + " is a Directory");

            }else if (lists.isFile()) {
                System.out.println(lists.getName() + " is a File");
            }
        }


    }
}
