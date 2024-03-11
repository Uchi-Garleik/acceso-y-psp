package Centurion;

import java.io.*;
import java.net.Socket;

public class Centurion {
    public Centurion(String host, int port){
        try (Socket server = new Socket(host, port)) {
            InputStream inputStream = server.getInputStream();
            OutputStream outputStream = server.getOutputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            System.out.println(dataInputStream.readUTF()); // Leer & imprime del socket el ACK "server"
            System.out.println(dataInputStream.readUTF()); // Leer & imprime del socket el ACK "server"


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
