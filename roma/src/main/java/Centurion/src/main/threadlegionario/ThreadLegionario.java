package Centurion.src.main.threadlegionario;

import Centurion.src.main.centurionserver.CenturionServer;
import Legionario.src.main.legionario.Legionario;

import java.io.*;
import java.net.Socket;

public class ThreadLegionario extends Thread{
    private Socket socketCliente;
    boolean tr = true;
    private CenturionServer centurionServer;
    public ThreadLegionario(Socket socketCliente, CenturionServer centurionServer){
        this.socketCliente = socketCliente;
        this.centurionServer = centurionServer;
    }


    @Override
    public void run() {
        try (
                OutputStream outputStream = socketCliente.getOutputStream();
                InputStream inputStream = socketCliente.getInputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ) {
            dataOutputStream.writeUTF("\"[" + socketCliente.getInetAddress() + "] Conexión establecida exitosamente.");
            int idLegionario = dataInputStream.readInt();
            Legionario legionario = (Legionario) objectInputStream.readObject();
            centurionServer.addLegionario(idLegionario, legionario);

            while (tr){
                dataOutputStream.writeUTF("Seleccione una operación:\n[1] Sumaer\n[2] Raíz cuadrada\n[3] Continuar serie\n[4] Salir");
                int operation = dataInputStream.readInt();
                switch (operation){
                    case 1:
                        break;
                    case 4:
                        socketCliente.close();
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socketCliente.close();
                System.out.println("[" + socketCliente.getLocalSocketAddress() + "] Conexión cerrada exitosamente.");
            } catch (IOException e) {
                System.out.println("[" + socketCliente.getLocalSocketAddress() + "] Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
