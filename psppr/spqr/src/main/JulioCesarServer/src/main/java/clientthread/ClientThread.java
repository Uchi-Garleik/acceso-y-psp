package clientthread;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {
    private Socket socketCliente;

    public ClientThread(Socket socketCliente){
        this.socketCliente = socketCliente;
    }


    @Override
    public void run() {
        try(
                OutputStream outputStream = socketCliente.getOutputStream();
                InputStream inputStream = socketCliente.getInputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream);)
        {
            dataOutputStream.writeUTF("\"[" + socketCliente.getInetAddress() + "] Conexión establecida exitosamente.");
            while (true) {
                dataOutputStream.writeUTF("Seleccione una operación:\n[1] Sumar\n[2] Raíz cuadrada\n[3] Continuar serie\n[4] Salir");
                int operation = dataInputStream.readInt();
                switch (operation){
                    case 3:
                        System.out.println("w");
                        break;
                }
            }
        }catch (IOException e) {
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

