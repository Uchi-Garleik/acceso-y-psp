package Legionario.src.main.legionario;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class Legionario implements Serializable {
    private enum Roles{
        explorador,
        logistica,
        soldado
    }
    private static int counterLegionarios;
    private int id;
    private String rol;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Legionario(){}
    public Legionario(String host, int port){
        counterLegionarios++;
        id = counterLegionarios;
        try {
            setRandomRol();
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

            while (true){
                System.out.println(reader.readUTF());
                int operation = new Scanner(System.in).nextInt();
                writer.writeInt(operation);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setRandomRol(){
        Random random = new Random();
        Roles[] roles = Roles.values();
        this.rol = String.valueOf(roles[random.nextInt(roles.length)]);
        System.out.println(this.rol);
    }

}
