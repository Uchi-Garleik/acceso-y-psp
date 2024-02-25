package Server.server;

import Server.clientmanager.ClientManager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 5000;
    public Server() {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("listening in port 5000");
            Socket clientSocket = serverSocket.accept();
            ClientManager clientManager = new ClientManager(clientSocket);
            clientManager.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
