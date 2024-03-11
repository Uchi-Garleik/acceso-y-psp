package Centurion.src.main.java.centurionservidor;

import Centurion.src.main.java.centurion.Centurion;
import JulioCesar.src.main.java.clientthread.ClientThread;
import JulioCesar.src.main.java.juliocesar.JulioCesar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class CenturionServidor {
    int port;
    public CenturionServidor(){
        this.port = JulioCesar.loadPortFromConfig() + Centurion.getContadorCenturion();
        System.out.println(port);
//        try (ServerSocket server = new ServerSocket()) {
//            System.out.println("Servidor escuchando en el puerto " + port);
//            while (true) {
//                Socket client = server.accept(); // Bloqueante
//                System.out.println("[" + server.getLocalSocketAddress() + "] Cliente aceptado.");
//
//                ClientThread clientThread = new ClientThread(client);
//                clientThread.start();
//            }
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
    }

    private static int loadPortFromConfig() {
        try (InputStream input = JulioCesar.class.getClassLoader().getResourceAsStream("centurion.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find centurion.properties");
                return -1; // Or handle the error as needed
            }

            prop.load(input);
            return Integer.parseInt(prop.getProperty("centurion.totalcount"));
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return -1; // Or handle the error as needed
        }
    }

    public static void main(String[] args) {
//        CenturionServidor centurionServidor = new CenturionServidor();
        System.out.println(loadPortFromConfig());
    }

}
