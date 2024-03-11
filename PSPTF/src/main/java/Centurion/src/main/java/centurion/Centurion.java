package Centurion.src.main.java.centurion;

import JulioCesar.src.main.java.juliocesar.JulioCesar;
import Legionario.src.main.java.legionario.Legionario;
import encriptacion.Enigma;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class Centurion {
    private static int contadorCenturion;
    private int number;
    public Centurion(String host, int port){
        contadorCenturion++;
        generateAndWritePortToConfig(contadorCenturion);
        this.number = contadorCenturion;
        System.out.println(this.number);
        try {
            Socket server = new Socket(host, port);
            InputStream inputStream = server.getInputStream();
            DataInputStream reader = new DataInputStream(inputStream);
            OutputStream outputStream = server.getOutputStream();
            DataOutputStream writer = new DataOutputStream(outputStream);
            System.out.println(Enigma.decryptData(reader.readUTF(), JulioCesar.getEncriptacionClave()));


            Legionario legionario = new Legionario(port+contadorCenturion);
            if (getContadorCenturion() == 3){
                boolean tr = true;
                while (tr){

                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateAndWritePortToConfig(int serverPort) {
        try (OutputStream output = new FileOutputStream("src/main/resources/centurion.properties")) {
            Properties properties = new Properties();
            properties.setProperty("centurion.totalcount", String.valueOf(serverPort));
            properties.store(output, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getContadorCenturion() {
        return contadorCenturion;
    }

    public static void setContadorCenturion(int contadorCenturion) {
        Centurion.contadorCenturion = contadorCenturion;
    }
}
