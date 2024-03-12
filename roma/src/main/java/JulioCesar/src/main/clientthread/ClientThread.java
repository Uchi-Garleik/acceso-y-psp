package JulioCesar.src.main.clientthread;

import Centurion.src.main.centurion.Centurion;
import JulioCesar.src.main.juliocesar.JulioCesar;
import encriptacion.Enigma;
import orden.Cosa;
import orden.Orden;
import orden.OrdenTraerCosa;
import orden.TipoOrden;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ClientThread extends Thread implements Serializable {
    private Socket socketCliente;
    boolean tr = true;
    private JulioCesar julioCesar;
    public ClientThread(Socket socketCliente, JulioCesar julioCesar){
        this.socketCliente = socketCliente;
        this.julioCesar = julioCesar;
    }
    Scanner scanner = new Scanner(System.in);

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
            Random random = new Random();
            dataOutputStream.writeUTF("\"[" + socketCliente.getInetAddress() + "] Conexión establecida exitosamente.");
            int idCenturion = dataInputStream.readInt();
            Centurion centurionaux = (Centurion) objectInputStream.readObject();
            System.out.println(centurionaux.getId());

            while (tr){
                dataOutputStream.writeUTF("Seleccione una operación:\n[1] Sumar\n[2] Raíz cuadrada\n[3] Continuar serie\n[4] Salir");
                int operation = dataInputStream.readInt();
                switch (operation){
                    case 1:
                        String tipoOrden;
                        tipoOrden = String.valueOf(TipoOrden.values()[random.nextInt(TipoOrden.values().length)]);
                        dataOutputStream.writeUTF(tipoOrden);
                        System.out.println(tipoOrden);
                        switch (tipoOrden){
                            case "TRAER_COSA":
                                Orden orden;
                                Cosa cosa = Cosa.values()[random.nextInt(Cosa.values().length)];
                                int cantidad = random.nextInt(10)+1;
                                System.out.println(String.valueOf(cosa));
                                if (String.valueOf(cosa).equals("PAPEL_PERGAMINO")){
                                    orden = new OrdenTraerCosa(cosa);
                                    dataOutputStream.writeUTF("cosa."+String.valueOf(cosa));
                                }else{
                                    orden = new OrdenTraerCosa(cosa, cantidad);
                                    dataOutputStream.writeUTF("cosa."+String.valueOf(cosa)+".cantidad."+cantidad);
                                }
                                break;
                            case "MANDAR_MENSAJE":
                                System.out.println("Que mensaje quieres mandar?");
                                String mensaje = scanner.nextLine();
//                                scanner.nextLine();
                                System.out.println("A que tipo de personaje se lo quieres mandar");
                                String receptor = scanner.nextLine();
//                                scanner.nextLine();
                                System.out.println("A que ID de personaje se lo quieres mandar");
                                String idReceptor = scanner.nextLine();
//                                scanner.nextLine();
                                dataOutputStream.writeUTF("receptor."+receptor+".id."+receptor+".mensaje."+mensaje);
                                break;
                            case "VIGILAR":
                                System.out.println("¿Que quieres vigilar?");
                                String objetoVigilar = scanner.nextLine();
                                scanner.nextLine();
                                System.out.println("¿Durante cuantos segundos?");
                                int tiempoVigilar = scanner.nextInt();
                                dataOutputStream.writeUTF("objeto."+objetoVigilar+".tiempo."+tiempoVigilar);
                                break;
                        }
                        break;
                    case 3:

                        break;
                    case 4:
                        socketCliente.close();
                        break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
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

