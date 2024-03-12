package JulioCesar.src.main.juliocesar;

import Centurion.src.main.centurion.Centurion;
import JulioCesar.src.main.clientthread.ClientThread;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JulioCesar {
    private static int encriptacionClave = 3;
    private Map<Integer, Centurion> centurionMap;

    public void addCenturion(int centurionID, Centurion centurion){ centurionMap.put(centurionID, centurion); }

    public JulioCesar(int port){
        this.centurionMap = new HashMap<>();
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en el puerto " + port);
            while (true) {
                Socket client = server.accept(); // Bloqueante
                System.out.println("[" + server.getLocalSocketAddress() + "] Cliente aceptado.");
                ClientThread clientThread = new ClientThread(client, this);
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getEncriptacionClave() {
        return encriptacionClave;
    }

    public static void setEncriptacionClave(int encriptacionClave) {
        JulioCesar.encriptacionClave = encriptacionClave;
    }

    public static int loadPortFromConfig() {
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
