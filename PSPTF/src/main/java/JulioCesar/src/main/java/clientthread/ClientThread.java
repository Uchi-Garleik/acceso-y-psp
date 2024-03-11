package JulioCesar.src.main.java.clientthread;

import JulioCesar.src.main.java.juliocesar.JulioCesar;
import encriptacion.Enigma;

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
        try (
                OutputStream outputStream = socketCliente.getOutputStream();
                InputStream inputStream = socketCliente.getInputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                DataInputStream dataInputStream = new DataInputStream(inputStream);
        ) {
            dataOutputStream.writeUTF(Enigma.encryptData("galia", JulioCesar.getEncriptacionClave()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                socketCliente.close();
                System.out.println("[" + socketCliente.getLocalSocketAddress() + "] Conexión cerrada exitosamente.");
            } catch (IOException e) {
                System.out.println("[" + socketCliente.getLocalSocketAddress() + "] Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
