package Server.clientmanager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientManager extends Thread{
    Socket clientSocket;
    public ClientManager(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (OutputStream outputStream = clientSocket.getOutputStream();
             DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            ){
              getMenuOptions(dataOutputStream);
            while (true){

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void getMenuOptions(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF("====================");
        dataOutputStream.writeUTF("1- Suma De Dos Numeros");
        dataOutputStream.writeUTF("2- Raiz Cuadrada De Un Numero");
        dataOutputStream.writeUTF("3- Continuacion Serie");
        dataOutputStream.writeUTF("4- Cerrar Sesion");
        dataOutputStream.writeUTF("====================");
    }
}
