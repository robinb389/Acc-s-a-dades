package view;

public class MenuView {

    public static void showStartMenu() {
        System.out.println("\n===== GESTOR DE LLIBRES =====");
        System.out.println("1. Iniciar sessió");
        System.out.println("2. Registrar-se");
        System.out.println("0. Sortir");
        System.out.print("Tria una opció: ");
    }

    public static void showMainMenu(boolean isAdmin) {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Gestionar llibres");
        System.out.println("2. Cercar llibres");
        if (isAdmin) {
            System.out.println("3. Gestionar usuaris");
            System.out.println("4. Veure registres d'accés");
        }
        System.out.println("0. Tancar sessió");
        System.out.print("Tria una opció: ");
    }

    public static void showBookMenu(boolean isAdmin) {
        System.out.println("\n===== GESTIÓ DE LLIBRES =====");
        System.out.println("1. Afegir llibre");
        System.out.println("2. Cercar per ISBN");
        System.out.println("3. Llistar tots els llibres");
        if (isAdmin) {
            System.out.println("4. Eliminar llibre");
        }
        System.out.println("0. Tornar al menú principal");
        System.out.print("Tria una opció: ");
    }

    public static void showUserMenu() {
        System.out.println("\n===== GESTIÓ D'USUARIS =====");
        System.out.println("1. Registrar nou usuari");
        System.out.println("2. Llistar usuaris");
        System.out.println("0. Tornar al menú principal");
        System.out.print("Tria una opció: ");
    }

    public void showInvalidOption() {
        System.out.println(" Opció invàlida, torna a intentar-ho.");
    }

    public void showWelcome(String username, boolean isAdmin) {
        System.out.println("\n Benvingut/da, " + username + "!");
        System.out.println("Rol: " + (isAdmin ? "Administrador" : "Estudiant"));
    }

    public void showGoodbye(String username) {
        System.out.println("\n Fins aviat, " + username + "!");
    }
}
