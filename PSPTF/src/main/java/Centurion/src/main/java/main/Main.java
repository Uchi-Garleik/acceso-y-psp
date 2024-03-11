package Centurion.src.main.java.main;

import Centurion.src.main.java.centurion.Centurion;
import JulioCesar.src.main.java.juliocesar.JulioCesar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            Centurion centurion = new Centurion("localhost",loadPortFromConfig());
        }
    }
    private static int loadPortFromConfig() {
        try (InputStream input = JulioCesar.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return -1; // Or handle the error as needed
            }

            prop.load(input);
            return Integer.parseInt(prop.getProperty("server.port"));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1; // Or handle the error as needed
        }
    }
}
