import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;
public class Examenuf1_RobinbirSingh {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean found = false;

        // Ask for username and password
        while (!found) {
            System.out.print("Enter here username: ");
            String username = sc.nextLine();
            System.out.print("Enter here password: ");
            String password = sc.nextLine();

            // Reading user name and password
            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] check = line.split("#");
                    if (check.length == 2 && check[0].equals(username)) {
                        if (check[1].equals(password)) {
                            found = true;
                            break; 
                        } else {
                            System.out.println("Password incorrect. Please try again.");
                            break; 
                        }
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("The file was not found.");
            } catch (IOException e) {
                System.out.println("An error while reading the file.");
            }

            if (!found) {
                System.out.println("Username not found. Do you want to create a new user? (yes/no)");
                String response = sc.nextLine();
                if (response.equals("yes") || response.equals("Yes")) {
                    addUser(username, password);
                    found = true; 
                } else {
                    System.out.println("Please try again.");
                }
            }
        }

        if (found) {
            System.out.println("User found!!");
            ShowProducts();
            editProduct(sc);
        }

        sc.close(); 
    }

    // Writing user name and password if it does not exsit
    private static void addUser(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            writer.write(username + "#" + password);
            writer.newLine();
            System.out.println("User added successfully!");
        } catch (IOException e) {
            System.out.println("Error while adding the user.");
        }
    }

    // Showing the Products when login is success
    private static void ShowProducts() {
       try (RandomAccessFile raf = new RandomAccessFile("shop.bin", "r")) {
            long fileLength = raf.length();
            if (fileLength % 38 != 0) {
                System.out.println("Invalid file format.");
                return;
            }

            int numberOfProducts = (int) (fileLength / 38);
            for (int i = 0; i < numberOfProducts; i++) {
                raf.seek(i * 38);

                // code
                int code = raf.readInt();

                // name
                byte[] nameBytes = new byte[30];
                raf.readFully(nameBytes);
                String name = new String(nameBytes).trim();

                // price
                float price = raf.readFloat();

                System.out.println("Product code: " + code);
                System.out.println("Product Name: " + name);
                System.out.println("Product Price: €" + price);
                System.out.println("-----------------------------");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
    }

    // edit the product
    private static void editProduct(Scanner sc) {
        try (RandomAccessFile raf = new RandomAccessFile("shop.bin", "rw")) {
            long fileLength = raf.length();
            if (fileLength % 38 != 0) { 
                System.out.println("Invalid file format.");
                return;
            }

            System.out.print("Enter the product number to edit: ");
            int productNumber = sc.nextInt();
            sc.nextLine(); 

            raf.seek((productNumber - 1) * 36);

            int code = raf.readInt();

            byte[] nameBytes = new byte[30];
            raf.readFully(nameBytes);
            String name = new String(nameBytes).trim();

            System.out.println("Product selected:");
            System.out.println("Name: " + name);
          
            System.out.print("Do you want to edit this product? (yes/no): ");
            String response = sc.nextLine();

            if (response.equals("yes") || response.equals("Yes")) {

                raf.seek((productNumber - 1) * 38);

                raf.writeInt(code);
                
                System.out.print("Enter new name: ");
                String newName = sc.nextLine();
                raf.writeUTF(newName);

                System.out.print("Enter new Price: ");
                float newprice = sc.nextFloat();
                raf.writeFloat(newprice);

                saveLog(code);
            }
        } catch (FileNotFoundException e) {
            System.out.println("The shop.bin file was not found.");
        } catch (IOException e) {
            System.out.println("An error while editing the product.");
        }
    }

    // To save the log when any product is changed
    private static void saveLog(int code) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream("log.bin", true))) {
            dos.writeUTF(ActualDate());
            dos.writeInt(code);
        } catch (IOException e) {
            System.out.println("An error while saving log.");
        }
    }

    // To search the actual date
    private static String ActualDate() {
        LocalDate today = LocalDate.now();
        return today.toString();
    }

}