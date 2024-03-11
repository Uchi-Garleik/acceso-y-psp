package JulioCesar;

import clientthread.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class JulioCesar {
    public JulioCesar(int port){
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Servidor escuchando en el puerto " + port);
            while (true) {
                Socket client = server.accept(); // Bloqueante
                System.out.println("[" + server.getLocalSocketAddress() + "] Cliente aceptado.");

                ClientThread clientThread = new ClientThread(client);
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
