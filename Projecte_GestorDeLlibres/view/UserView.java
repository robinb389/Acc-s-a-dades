package view;

import java.util.List;
import model.User;

public class UserView {

    public void showLoginForm() {
        System.out.println("\n===== INICIAR SESSIÓ =====");
    }

    public void showRegisterForm() {
        System.out.println("\n===== REGISTRAR-SE =====");
    }

    public void showLoginSuccess(User user) {
        System.out.println(" Benvingut/da, " + user.getName() + " (" + user.getUsername() + ")!");
    }

    public void showLoginError() {
        System.out.println(" Usuari o contrasenya incorrectes. Torna a intentar-ho.");
    }

    public void showRegisterSuccess(User user) {
        System.out.println(" Usuari \"" + user.getUsername() + "\" (" + user.getName() + ") registrat correctament!");
    }

    public void showUserExists() {
        System.out.println(" El nom d'usuari ja existeix! Tria un altre nom d'usuari.");
    }

    public void showAllUsers(List<User> users) {
        System.out.println("\n===== LLISTA D'USUARIS =====");
        if (users.isEmpty()) {
            System.out.println("No hi ha usuaris registrats.");
        } else {
            for (int i = 0; i < users.size(); i++) {
                System.out.println((i + 1) + ". " + users.get(i));
            }
            System.out.println("\nTotal: " + users.size() + " usuaris");
        }
    }

    public void promptForUsername() {
        System.out.print("Nom d'usuari: ");
    }

    public void promptForPassword() {
        System.out.print("Contrasenya: ");
    }

    public void promptForName() {
        System.out.print("Nom complet: ");
    }

    public void promptForRole() {
        System.out.print("És administrador? (s/n): ");
    }

    public void showInvalidRole() {
        System.out.println(" Opció invàlida. Introdueix 's' per administrador o 'n' per estudiant.");
    }

    public void showReturningToMain() {
        System.out.println("↩ Tornant al menú principal...");
    }
}
