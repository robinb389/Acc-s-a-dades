package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Book;
import view.BookView;

/**
 * Controller para la gestión de libros.
 * Orquesta entre el modelo (persistencia) y la View (presentación).
 */
public class BookController {
    
    private final String FILE_NAME = "Books/books.dat";
    private final int ISBN_SIZE = 13;
    private final int TITLE_SIZE = 50;
    private final int AUTHOR_SIZE = 30;
    private BookView bookView;

    public BookController(BookView bookView) {
        this.bookView = bookView;
        File booksDir = new File("Books");
        if (!booksDir.exists()) {
            booksDir.mkdirs();
        }
    }

    /**
     * Guarda un libro en el archivo.
     */
    private void save(Book book) throws IOException {
        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
            raf.seek(raf.length());
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_NAME, true))) {
                dos.writeUTF(fixLength(book.getIsbn(), ISBN_SIZE));
                dos.writeUTF(fixLength(book.getTitle(), TITLE_SIZE));
                dos.writeUTF(fixLength(book.getAuthor(), AUTHOR_SIZE));
            }
        }
    }

    /**
     * Busca un libro por ISBN.
     */
    private Book findByISBN(String isbn) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_NAME))) {
            while (dis.available() > 0) {
                try {
                    String currentISBN = dis.readUTF().trim();
                    String title = dis.readUTF().trim();
                    String author = dis.readUTF().trim();
                    
                    if (currentISBN.equals(isbn)) {
                        return new Book(author, currentISBN, title);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe aún, retorna null
        }
        return null;
    }

    /**
     * Obtiene todos los libros.
     */
    private List<Book> findAll() throws IOException {
        List<Book> books = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_NAME))) {
            while (dis.available() > 0) {
                try {
                    String isbn = dis.readUTF().trim();
                    String title = dis.readUTF().trim();
                    String author = dis.readUTF().trim();
                    books.add(new Book(author, isbn, title));
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe aún, retorna lista vacía
        }
        return books;
    }

    /**
     * Elimina un libro por ISBN.
     */
    private boolean delete(String isbn) throws IOException {
        List<Book> books = findAll();
        Book toDelete = null;
        
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                toDelete = book;
                break;
            }
        }
        
        if (toDelete == null) {
            return false;
        }
        
        // Reescribe el archivo sin el libro eliminado
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(FILE_NAME))) {
            for (Book book : books) {
                if (!book.getIsbn().equals(isbn)) {
                    dos.writeUTF(fixLength(book.getIsbn(), ISBN_SIZE));
                    dos.writeUTF(fixLength(book.getTitle(), TITLE_SIZE));
                    dos.writeUTF(fixLength(book.getAuthor(), AUTHOR_SIZE));
                }
            }
        }
        return true;
    }

    private String fixLength(String str, int length) {
        if (str.length() > length) return str.substring(0, length);
        return String.format("%-" + length + "s", str);
    }

    // ========== Public Methods ==========

    /**
     * Añade un libro nuevo.
     */
    public void addBook(String isbn, String title, String author) {
        try {
            if (findByISBN(isbn) != null) {
                bookView.showBookAlreadyExists(isbn);
                return;
            }
            
            Book book = new Book(author, isbn, title);
            save(book);
            bookView.showAddBookSuccess(book);
        } catch (IOException e) {
            bookView.showAddBookError(e.getMessage());
        }
    }

    /**
     * Busca un libro por ISBN.
     */
    public Book findBookByISBN(String isbn) {
        try {
            return findByISBN(isbn);
        } catch (IOException e) {
            bookView.showSearchBookError(e.getMessage());
            return null;
        }
    }

    /**
     * Obtiene todos los libros.
     */
    public List<Book> getAllBooks() {
        try {
            return findAll();
        } catch (IOException e) {
            bookView.showListBooksError(e.getMessage());
            return List.of();
        }
    }

    /**
     * Elimina un libro por ISBN.
     */
    public void deleteBook(String isbn) {
        try {
            Book book = findByISBN(isbn);
            if (book == null) {
                bookView.showDeleteBookError(isbn);
                return;
            }
            
            if (delete(isbn)) {
                bookView.showDeleteBookSuccess(book.getTitle());
            } else {
                bookView.showDeleteBookError(isbn);
            }
        } catch (IOException e) {
            bookView.showDeleteBookError(e.getMessage());
        }
    }
}
