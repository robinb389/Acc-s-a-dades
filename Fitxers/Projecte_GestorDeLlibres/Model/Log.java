package Projecte_GestorDeLlibres.Model;

public class Log {
    
    private String timestamp;
    private String username;
    private String action;

    public Log(String action, String timestamp, String username) {
        this.action = action;
        this.timestamp = timestamp;
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAction(String action) {
        this.action = action;
    }


}
