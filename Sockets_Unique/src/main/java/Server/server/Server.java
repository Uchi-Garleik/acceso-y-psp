package Server.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private static final int PORT = 5000;

    public Server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("listening in port 5000");

            Socket clientSocket = serverSocket.accept();

            OutputStream outputStream = clientSocket.getOutputStream();
            InputStream inputStream = clientSocket.getInputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while (true){
                dataOutputStream.writeUTF("=======================\n[1] Suma De Dos Numeros\n[2] Raiz cuadrada de un numero\n[3] Continuacion De Serie\n[4] Cerrar Sesion\n=======================");
                int number = dataInputStream.readInt();
                double operacion = 0;
                switch (number){
                    case 1:
                        for (int i = 0; i < 2; i++) {
                            dataOutputStream.writeUTF("Inserte el " + (i+1) + "º numero");
                            operacion += dataInputStream.readDouble();
                        }
                        dataOutputStream.writeUTF("Sumando. . .");
//                        int[] sumaArray = new int[2];
//
//                        for (int i = 0; i < 2; i++) {
//                            dataOutputStream.writeUTF("Introduce el " + i+1 + "º numero para sumar");
//                            sumaArray[i] = dataInputStream.readInt();
//                        }
                        break;
                    default:
                        System.out.println(number);
                }
            }

            // close
            //outputStream.close();
            //dataOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

