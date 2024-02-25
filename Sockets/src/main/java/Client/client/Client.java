package Client.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    static final String HOST = "localhost";
    static final int PORT = 5000;

    public Client() {
        try {
            Socket socketCliente = new Socket(HOST, PORT);

            InputStream inputStream = socketCliente.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while (dataInputStream.available()>0){
                System.out.println(dataInputStream.readUTF());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
