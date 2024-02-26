package Client.client;

import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Client {
    static final String HOST = "localhost";
    static final int PORT = 5000;

    public Client() {
        try (Socket socketCliente = new Socket(HOST, PORT);){
            InputStream inputStream = socketCliente.getInputStream();
            OutputStream outputStream = socketCliente.getOutputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(System.in)));
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println(dataInputStream.readUTF());
                int number = scanner.nextInt();
                dataOutputStream.writeInt(number);
                switch (number){
                    case 1:
                        System.out.println(dataInputStream.readUTF());
                        dataOutputStream.writeDouble(scanner.nextDouble());
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
