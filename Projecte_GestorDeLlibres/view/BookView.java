package view;

import java.util.List;
import model.Book;

public class BookView {
    
    public void showAddBookForm() {
        System.out.println("\n===== AFEGIR LLIBRE =====");
    }

    public void showSearchBookForm() {
        System.out.println("\n===== CERCAR LLIBRE =====");
        System.out.print("Introdueix l'ISBN del llibre: ");
    }

    public void showAddBookSuccess(Book book) {
        System.out.println(" Llibre afegit correctament: " + book);
    }

    public void showBookAlreadyExists(String isbn) {
        System.out.println(" Error: El llibre amb ISBN " + isbn + " ja existeix.");
    }

    public void showAddBookError(String message) {
        System.out.println(" Error afegint el llibre: " + message);
    }

    public void showDeleteBookSuccess(String title) {
        System.out.println(" Llibre eliminat correctament: " + title);
    }

    public void showDeleteBookError(String isbn) {
        System.out.println(" Error: Llibre amb ISBN " + isbn + " no trobat.");
    }

    public void showSearchBookError(String message) {
        System.out.println(" Error en la cerca: " + message);
    }

    public void showListBooksError(String message) {
        System.out.println(" Error obtenint la llista de llibres: " + message);
    }

    public void showBookNotFound(String isbn) {
        System.out.println(" No s'ha trobat cap llibre amb ISBN: " + isbn);
    }

    public void showBook(Book book) {
        if (book != null) {
            System.out.println("\n LLIBRE TROBAT:");
            System.out.println("   " + book);
        }
    }

    public void showAllBooks(List<Book> books) {
        System.out.println("\n===== LLISTA DE LLIBRES =====");
        if (books.isEmpty()) {
            System.out.println("No hi ha llibres registrats.");
        } else {
            for (int i = 0; i < books.size(); i++) {
                System.out.println((i + 1) + ". " + books.get(i));
            }
            System.out.println("\nTotal: " + books.size() + " llibres");
        }
    }

    public void showReturningToMain() {
        System.out.println("↩ Tornant al menú principal...");
    }

    public void promptForISBN() {
        System.out.print("ISBN (13 dígits): ");
    }

    public void promptForTitle() {
        System.out.print("Títol: ");
    }

    public void promptForAuthor() {
        System.out.print("Autor: ");
    }

}
