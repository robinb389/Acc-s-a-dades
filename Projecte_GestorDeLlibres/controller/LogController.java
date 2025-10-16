package controller;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogController {

    private final String FILE_NAME = "Logs/access.log";

    public LogController() {
        
        File logsDir = new File("Logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
    }

    public void log(String username, String action) {
        try (FileOutputStream fos = new FileOutputStream(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String logEntry = String.format("[%s] Usuari: %s - Acció: %s", timestamp, username, action);
            
            bw.write(logEntry);
            bw.newLine();
            bw.flush();
            
        } catch (IOException e) {
            System.out.println(" Error escrivint al log: " + e.getMessage());
        }
    }

    public void logLogin(String username, boolean success) {
        String action = success ? "Login exitós" : "Login fallit";
        log(username, action);
    }

    public void logBookAction(String username, String action, String bookInfo) {
        log(username, action + " - " + bookInfo);
    }

    public void logUserAction(String username, String action) {
        log(username, action);
    }

    public void showLogs() {
        System.out.println("\n=== REGISTRE D'ACCÉS ===");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null && count < 50) { // Show last 50 entries
                System.out.println(line);
                count++;
            }
            if (count == 50) {
                System.out.println("... (mostrant últims 50 registres)");
            }
        } catch (IOException e) {
            System.out.println("❌ Error llegint el log: " + e.getMessage());
        }
    }
}
