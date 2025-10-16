package model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(boolean isAdmin, String name, String password, String username) {
        this.isAdmin = isAdmin;
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isIsAdmin() { return isAdmin; }

    @Override
    public String toString() {
        return String.format("Usuari: %s (%s) - %s", name, username, isAdmin ? "Admin" : "Estudiant");
    }
}
