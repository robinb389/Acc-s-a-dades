package controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.User;
import view.UserView;

public class UserController {

    private List<User> users;
    private UserView view;
    private final String FILE_NAME = "Users/users.dat";

    public UserController(UserView view) {
        this.view = view;
        
        File usersDir = new File("Users");
        if (!usersDir.exists()) {
            usersDir.mkdirs();
        }
        users = loadUsers(); 
    }

    
    public void registerUser(String name, String username, String password, boolean isAdmin) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                view.showUserExists();
                return;
            }
        }
        User newUser = new User(isAdmin, name, password, username);
        users.add(newUser);
        saveUsers(); 
        view.showRegisterSuccess(newUser);
    }

    
    public void loginUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username) && u.getPassword().equals(password)) {
                view.showLoginSuccess(u);
                return;
            }
        }
        view.showLoginError();
    }

    
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(users);
            System.out.println(" Usuaris desats correctament.");
        } catch (IOException e) {
            System.out.println(" Error desant usuaris: " + e.getMessage());
        }
    }

    
    @SuppressWarnings("unchecked")
    private List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println(" Cap usuari trobat. La llista és buida.");
            return list;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            list = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Error carregant usuaris: " + e.getMessage());
            list = new ArrayList<>();
        }
        return list;
    }

   
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
