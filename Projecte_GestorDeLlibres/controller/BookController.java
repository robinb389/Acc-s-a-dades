package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;

public class BookController {

    private final String FILE_NAME = "Books/books.dat";
    private final int ISBN_SIZE = 13;
    private final int TITLE_SIZE = 50;
    private final int AUTHOR_SIZE = 30;
    private final int RECORD_SIZE = ISBN_SIZE + TITLE_SIZE + AUTHOR_SIZE;

    public BookController() {
        
        File booksDir = new File("Books");
        if (!booksDir.exists()) {
            booksDir.mkdirs();
        }
    }

    public void addBook(String isbn, String title, String author) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            
            if (findBookByISBN(isbn) != null) {
                System.out.println(" Error: El llibre amb ISBN " + isbn + " ja existeix.");
                return;
            }
            
            raf.seek(raf.length());
            raf.writeUTF(fixLength(isbn, ISBN_SIZE));
            raf.writeUTF(fixLength(title, TITLE_SIZE));
            raf.writeUTF(fixLength(author, AUTHOR_SIZE));
            System.out.println(" Llibre afegit correctament: " + title);
        } catch (IOException e) {
            System.out.println(" Error afegint el llibre: " + e.getMessage());
        }
    }

    public Book findBookByISBN(String isbn) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                String currentISBN = raf.readUTF().trim();
                String title = raf.readUTF().trim();
                String author = raf.readUTF().trim();
                
                if (currentISBN.equals(isbn)) {
                    return new Book(author, currentISBN, title);
                }
            }
        } catch (IOException e) {
            
        }
        return null;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            while (raf.getFilePointer() < raf.length()) {
                String isbn = raf.readUTF().trim();
                String title = raf.readUTF().trim();
                String author = raf.readUTF().trim();
                books.add(new Book(author, isbn, title));
            }
        } catch (IOException e) {
            
        }
        return books;
    }

    public void deleteBook(String isbn) {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            long fileLength = raf.length();
            long currentPos = 0;
            
            while (currentPos < fileLength) {
                String currentISBN = raf.readUTF().trim();
                String title = raf.readUTF().trim();
                String author = raf.readUTF().trim();
                
                if (currentISBN.equals(isbn)) {
                    
                    long nextRecordPos = raf.getFilePointer();
                    
                   
                    byte[] buffer = new byte[(int)(fileLength - nextRecordPos)];
                    if (buffer.length > 0) {
                        raf.read(buffer);
                        raf.seek(currentPos);
                        raf.write(buffer);
                    }
                    
                    
                    raf.setLength(fileLength - RECORD_SIZE);
                    System.out.println(" Llibre eliminat correctament: " + title);
                    return;
                }
                currentPos = raf.getFilePointer();
            }
            System.out.println(" Error: Llibre amb ISBN " + isbn + " no trobat.");
        } catch (IOException e) {
            System.out.println(" Error eliminant el llibre: " + e.getMessage());
        }
    }

    private String fixLength(String str, int length) {
        if (str.length() > length) return str.substring(0, length);
        return String.format("%-" + length + "s", str);
    }
}
