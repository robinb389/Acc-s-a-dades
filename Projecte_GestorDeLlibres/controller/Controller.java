package controller;

import java.util.Scanner;
import model.Config;
import model.User;
import view.*;

public class Controller {
    
    private Scanner scanner;
    private UserController userController;
    private BookController bookController;
    private LogController logController;
    private UserView userView;
    private BookView bookView;
    private MenuView menuView;
    private User currentUser;
    private Config config;

    public Controller() {
        scanner = new Scanner(System.in);
        userView = new UserView();
        bookView = new BookView();
        menuView = new MenuView();
        config = new Config();
        
        userController = new UserController(userView);
        bookController = new BookController(bookView);
        logController = new LogController();
    }

    public static void main(String[] args) {
        Controller app = new Controller();
        app.run();
    }

    public void run() {
        System.out.println("   BENVINGUT AL GESTOR DE LLIBRES");
        System.out.println("Versió: " + config.getVersio() + " | Admin: " + config.getNomAdmin());
        System.out.println("2n DAM - Accés a Dades");
        
        while (true) {
            MenuView.showStartMenu();
            int option = getIntInput();
            
            switch (option) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleRegister();
                    break;
                case 0:
                    System.out.println(" Gràcies per utilitzar el Gestor de Llibres!");
                    return;
                default:
                    menuView.showInvalidOption();
            }
        }
    }

    private void handleLogin() {
        userView.showLoginForm();
        userView.promptForUsername();
        String username = scanner.nextLine();
        userView.promptForPassword();
        String password = scanner.nextLine();
        
        
        User user = findUserByCredentials(username, password);
        if (user != null) {
            currentUser = user;
            logController.logLogin(username, true);
            System.out.println("Benvingut/da " + user.getName());
            showMainMenu();
        } else {
            logController.logLogin(username, false);
            userView.showLoginError();
        }
    }

    private void handleRegister() {
        userView.showRegisterForm();
        userView.promptForName();
        String name = scanner.nextLine();
        userView.promptForUsername();
        String username = scanner.nextLine();
        userView.promptForPassword();
        String password = scanner.nextLine();
        userView.promptForRole();
        String roleInput = scanner.nextLine().toLowerCase();
        
        boolean isAdmin = false;
        if (roleInput.equals("s") || roleInput.equals("sí") || roleInput.equals("si")) {
            isAdmin = true;
        } else if (roleInput.equals("n") || roleInput.equals("no")) {
            isAdmin = false;
        } else {
            userView.showInvalidRole();
            return;
        }
        
        userController.registerUser(name, username, password, isAdmin);
        logController.logUserAction(username, "Usuari registrat");
    }

    private void showMainMenu() {
        while (currentUser != null) {
            MenuView.showMainMenu(currentUser.isIsAdmin());
            int option = getIntInput();
            
            switch (option) {
                case 1:
                    handleBookManagement();
                    break;
                case 2:
                    handleSearchBook();
                    break;
                case 3:
                    if (currentUser.isIsAdmin()) {
                        handleUserManagement();
                    } else {
                        menuView.showInvalidOption();
                    }
                    break;
                case 4:
                    if (currentUser.isIsAdmin()) {
                        logController.showLogs();
                    } else {
                        menuView.showInvalidOption();
                    }
                    break;
                case 5:
                    if (currentUser.isIsAdmin()) {
                        handleEditConfig();
                    } else {
                        menuView.showInvalidOption();
                    }
                    break;
                case 0:
                    logController.logUserAction(currentUser.getUsername(), "Sessió tancada");
                    menuView.showGoodbye(currentUser.getUsername());
                    currentUser = null;
                    break;
                default:
                    menuView.showInvalidOption();
            }
        }
    }

    private void handleBookManagement() {
        while (true) {
            MenuView.showBookMenu(currentUser.isIsAdmin());
            int option = getIntInput();
            
            switch (option) {
                case 1:
                    handleAddBook();
                    break;
                case 2:
                    handleSearchBook();
                    break;
                case 3:
                    handleListBooks();
                    break;
                case 4:
                    if (currentUser.isIsAdmin()) {
                        handleDeleteBook();
                    } else {
                        menuView.showInvalidOption();
                    }
                    break;
                case 0:
                    return;
                default:
                    menuView.showInvalidOption();
            }
        }
    }

    private void handleAddBook() {
        bookView.showAddBookForm();
        bookView.promptForISBN();
        String isbn = scanner.nextLine();
        bookView.promptForTitle();
        String title = scanner.nextLine();
        bookView.promptForAuthor();
        String author = scanner.nextLine();
        
        bookController.addBook(isbn, title, author);
        logController.logBookAction(currentUser.getUsername(), "Llibre afegit", title);
    }

    private void handleSearchBook() {
        bookView.showSearchBookForm();
        String isbn = scanner.nextLine();
        var book = bookController.findBookByISBN(isbn);
        
        if (book != null) {
            bookView.showBook(book);
            logController.logBookAction(currentUser.getUsername(), "Cerca de llibre", isbn);
        } else {
            bookView.showBookNotFound(isbn);
        }
    }

    private void handleListBooks() {
        var books = bookController.getAllBooks();
        bookView.showAllBooks(books);
        logController.logBookAction(currentUser.getUsername(), "Llista de llibres", "Veure tots");
    }

    private void handleDeleteBook() {
        bookView.showSearchBookForm();
        String isbn = scanner.nextLine();
        var book = bookController.findBookByISBN(isbn);
        
        if (book != null) {
            bookController.deleteBook(isbn);
            logController.logBookAction(currentUser.getUsername(), "Llibre eliminat", book.getTitle());
        } else {
            bookView.showDeleteBookError(isbn);
        }
    }

    private void handleUserManagement() {
        while (true) {
            MenuView.showUserMenu();
            int option = getIntInput();
            
            switch (option) {
                case 1:
                    handleRegister();
                    break;
                case 2:
                    var users = userController.getAllUsers();
                    userView.showAllUsers(users);
                    break;
                case 0:
                    return;
                default:
                    menuView.showInvalidOption();
            }
        }
    }

    private void handleEditConfig() {
        while (true) {
            System.out.println("\n--- Editar Configuració ---");
            System.out.println("1. Editar Versió (actualment: " + config.getVersio() + ")");
            System.out.println("2. Editar Nom Admin (actualment: " + config.getNomAdmin() + ")");
            System.out.println("0. Enrere");
            System.out.print("Opció: ");
            
            int option = getIntInput();
            
            switch (option) {
                case 1:
                    System.out.print("Nova versió: ");
                    String newVersio = scanner.nextLine();
                    config.setVersio(newVersio);
                    System.out.println("✓ Versió actualitzada");
                    logController.logUserAction(currentUser.getUsername(), "Versió actualitzada a " + newVersio);
                    break;
                case 2:
                    System.out.print("Nou nom admin: ");
                    String newNomAdmin = scanner.nextLine();
                    config.setNomAdmin(newNomAdmin);
                    System.out.println("✓ Nom admin actualitzat");
                    logController.logUserAction(currentUser.getUsername(), "Nom admin actualitzat a " + newNomAdmin);
                    break;
                case 0:
                    return;
                default:
                    menuView.showInvalidOption();
            }
        }
    }

    private User findUserByCredentials(String username, String password) {
        var users = userController.getAllUsers();
        for (User user : users) {
            if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Introdueix un número vàlid: ");
            }
        }
    }

    public Config getConfig() {
        return config;
    }
}
