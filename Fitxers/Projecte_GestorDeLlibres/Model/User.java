package Projecte_GestorDeLlibres.Model;

public class User {

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

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

        public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    
}
