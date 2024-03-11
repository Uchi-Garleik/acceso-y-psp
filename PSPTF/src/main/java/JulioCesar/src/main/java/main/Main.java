package JulioCesar.src.main.java.main;


import JulioCesar.src.main.java.juliocesar.JulioCesar;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Que puerto debe utilizarse");
        int serverPort = new Scanner(System.in).nextInt();
        generateAndWritePortToConfig(serverPort);
        JulioCesar julioCesar = new JulioCesar(loadPortFromConfig());
    }

    private static void generateAndWritePortToConfig(int serverPort) {
        try (OutputStream output = new FileOutputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.setProperty("server.port", String.valueOf(serverPort));
            properties.store(output, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
