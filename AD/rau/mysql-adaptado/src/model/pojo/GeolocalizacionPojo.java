package model.pojo;

import java.util.ArrayList;

public class GeolocalizacionPojo {
    private int id;
    private int idRepartidor;
    private String cordLat;
    private String cordLng;
    private String fecha;

    public GeolocalizacionPojo() { }

    public GeolocalizacionPojo(int id, int idRepartidor, String cordLat, String cordLng, String fecha) {
        this.id = id;
        this.idRepartidor = idRepartidor;
        this.cordLat = cordLat;
        this.cordLng = cordLng;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getCordLat() {
        return cordLat;
    }

    public void setCordLat(String cordLat) {
        this.cordLat = cordLat;
    }

    public String getCordLng() {
        return cordLng;
    }

    public void setCordLng(String cordLng) {
        this.cordLng = cordLng;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static String toJson(ArrayList<GeolocalizacionPojo> geolocalizacionResponse) {
        String json = "{\n";
        json += "\t\"geolocalizacion\": [\n";

        for (GeolocalizacionPojo geolocalizacion : geolocalizacionResponse) {
            json += "\t\t{\n";
            json += "\t\t\t\"id\": " + "\"" + geolocalizacion.getId() + "\",\n";
            json += "\t\t\t\"id_repartidor\": " + "\"" + geolocalizacion.getIdRepartidor() + "\",\n";
            json += "\t\t\t\"cord_lat\": " + "\"" + geolocalizacion.getCordLat() + "\",\n";
            json += "\t\t\t\"cord_lng\": " + "\"" + geolocalizacion.getCordLng() + "\",\n";
            json += "\t\t\t\"fecha\": " + "\"" + geolocalizacion.getFecha() + "\"\n";
            json += "\t\t},\n";
        }

        json += "\t]\n";
        json += "}\n";
        return json;
    }
}
