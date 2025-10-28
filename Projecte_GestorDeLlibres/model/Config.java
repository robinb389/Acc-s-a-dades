package model;

import java.io.*;


public class Config {
    
    private static final String CONFIG_FILE = "config.bin";
    
    private String versio;
    private String nomAdmin;
    
    public Config() {
        loadConfig();
    }
    
  
    private void loadConfig() {
        File configFile = new File(CONFIG_FILE);
        
        if (configFile.exists()) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(configFile))) {
                this.versio = dis.readUTF();
                this.nomAdmin = dis.readUTF();
            } catch (IOException e) {
                System.out.println("Error cargando configuración: " + e.getMessage());
                createDefaultConfig();
            }
        } else {
            createDefaultConfig();
        }
    }
    

    private void createDefaultConfig() {
        this.versio = "1.0";
        this.nomAdmin = "Admin";
        saveConfig();
    }
    

    public void saveConfig() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(CONFIG_FILE))) {
            dos.writeUTF(versio);
            dos.writeUTF(nomAdmin);
            dos.flush();
        } catch (IOException e) {
            System.out.println("Error guardando configuración: " + e.getMessage());
        }
    }
    
 
    public String getVersio() {
        return versio;
    }
    

    public void setVersio(String newVersio) {
        if (newVersio != null && !newVersio.trim().isEmpty()) {
            this.versio = newVersio;
            saveConfig();
        }
    }
    

    public String getNomAdmin() {
        return nomAdmin;
    }
    

    public void setNomAdmin(String newNomAdmin) {
        if (newNomAdmin != null && !newNomAdmin.trim().isEmpty()) {
            this.nomAdmin = newNomAdmin;
            saveConfig();
        }
    }
}