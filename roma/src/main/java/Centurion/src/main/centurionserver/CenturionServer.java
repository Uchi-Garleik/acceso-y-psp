package Centurion.src.main.centurionserver;

import Centurion.src.main.threadlegionario.ThreadLegionario;
import JulioCesar.src.main.clientthread.ClientThread;
import Legionario.src.main.legionario.Legionario;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CenturionServer implements Runnable{
    private Map<Integer, Legionario> legionarioMap;
    private final int PORT;
    public CenturionServer(int port){
        this.PORT = port;
        this.legionarioMap = new HashMap<>();
    }

    public void addLegionario(int legionarioID, Legionario legionario){
        legionarioMap.put(legionarioID,legionario);
    }
    public Legionario getCenturion(String legionarioID) {
        return legionarioMap.get(legionarioID);
    }
    public ArrayList<Legionario> findAllLegionarios() {
        return new ArrayList<>(legionarioMap.values());
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Servidor escuchando en el puerto " + PORT);
            while (true) {
                Socket client = server.accept(); // Bloqueante
                System.out.println("[" + server.getLocalSocketAddress() + "] Cliente aceptado.");
                ThreadLegionario threadLegionario = new ThreadLegionario(client, this);
                threadLegionario.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mandarOrden(String s) {

    }
}
