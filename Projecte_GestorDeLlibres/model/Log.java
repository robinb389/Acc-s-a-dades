package model;
import java.time.Instant;

public class Log {
    
    private Instant timestamp;
    private String username;
    private String action;

    public Log(String action, String timestamp, String username) {
        this.timestamp = Instant.now();
        this.username = username;
        this.action = action;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public String getAction() {
        return action;
    }


    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setAction(String action) {
        this.action = action;
    }
   
}
