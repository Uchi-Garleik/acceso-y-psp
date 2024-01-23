package model.pojo;

import java.util.ArrayList;

public class RepartidorPojo {
    private int id;
    private String dni;
    private String nombre;
    private String email;

    public RepartidorPojo() { }

    public RepartidorPojo(int id, String dni, String nombre, String email) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String toJson(ArrayList<RepartidorPojo> repartidoresResponse) {
        String json = "{\n";
        json += "\t\"repartidores\": [\n";

        for (RepartidorPojo repartidor : repartidoresResponse) {
            json += "\t\t{\n";
            json += "\t\t\t\"id\": " + "\"" + repartidor.getId() + "\",\n";
            json += "\t\t\t\"dni\": " + "\"" + repartidor.getDni() + "\",\n";
            json += "\t\t\t\"nombre\": " + "\"" + repartidor.getNombre() + "\",\n";
            json += "\t\t\t\"email\": " + "\"" + repartidor.getEmail() + "\"\n";
            json += "\t\t},\n";
        }

        json += "\t]\n";
        json += "}\n";
        return json;
    }
}
