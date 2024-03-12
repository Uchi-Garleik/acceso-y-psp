package Centurion.src.main.centurion;

import Centurion.src.main.centurionserver.CenturionServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import Legionario.src.main.legionario.Legionario;
import orden.Cosa;
import orden.Orden;
import orden.OrdenTraerCosa;
import orden.TipoOrden;

public class Centurion implements Serializable{
    private String[] info;
    private static int centurionCounter;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Centurion(String host, int port){
        centurionCounter++;
        id = centurionCounter;
        try {
            Socket server = new Socket(host, port);
            InputStream inputStream = server.getInputStream();
            DataInputStream reader = new DataInputStream(inputStream);
            OutputStream outputStream = server.getOutputStream();
            DataOutputStream writer = new DataOutputStream(outputStream);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            System.out.println(reader.readUTF());
            writer.writeInt(getId());
            objectOutputStream.writeObject(this);


            CenturionServer centurionServer = new CenturionServer(4001);
            Thread serverThread = new Thread(centurionServer);
            serverThread.start();
            while (true){
                System.out.println(reader.readUTF());
                int operation = new Scanner(System.in).nextInt();
                writer.writeInt(operation);
                switch (operation){
                    case 1:
                        String tipoOrden = reader.readUTF();
                        Legionario legionarioFound = checkOrder(centurionServer, tipoOrden);
                        if (legionarioFound != null){
                            String orden = "";
                            switch (tipoOrden){
                                case "TRAER_COSA":
                                    orden = reader.readUTF();
                                    centurionServer.mandarOrden(reader.readUTF());
                                    break;
                                case "MANDAR_MENSAJE":
                                    orden = reader.readUTF();
                                    break;
                                case "VIGILAR":
                                    orden = reader.readUTF();
                                    break;
                            }
                        }
                        break;
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Legionario checkOrder(CenturionServer centurionServer, String tipoOrden){
        ArrayList<Legionario> legionarios = centurionServer.findAllLegionarios();
        for (Legionario legionario : legionarios) {
            switch (legionario.getRol()) {
                case "explorador":
                    if (tipoOrden.equals("MANDAR_MENSAJE")) {
                        System.out.println("Legionario " + legionario.getRol() + " is compatible with the order.");
                        // Perform additional actions or return true if a compatible legionario is found
                        return legionario;
                    }
                    break;
                case "logistica":
                    if (tipoOrden.equals("TRAER_COSA")) {
                        System.out.println("Legionario " + legionario.getRol() + " is compatible with the order.");
                        // Perform additional actions or return true if a compatible legionario is found
                        return legionario;
                    }
                    break;
                case "soldado":
                    if (tipoOrden.equals("VIGILAR")) {
                        System.out.println("Legionario " + legionario.getRol() + " is compatible with the order.");
                        // Perform additional actions or return true if a compatible legionario is found
                        return legionario;
                    }
                    break;
            }
        }

        // If no compatible legionario is found
        System.out.println("No compatible legionario found for the order.");
        return null;
    }

}
