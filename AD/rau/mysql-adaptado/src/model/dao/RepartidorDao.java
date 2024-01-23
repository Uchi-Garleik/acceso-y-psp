package model.dao;

import model.SqlMotor;
import model.pojo.GeolocalizacionPojo;
import model.pojo.RepartidorPojo;

import java.sql.ResultSet;
import java.util.ArrayList;

public class RepartidorDao {
    private SqlMotor motor;
    private final String
            SQL_SELECT = "SELECT * FROM repartidor WHERE 1 = 1",
            SQL_SELECT_HISTORY = "SELECT * FROM geolocalizacion WHERE 1 = 1";

    public RepartidorDao() {
        this.motor = new SqlMotor();
    }

    public ArrayList<RepartidorPojo> get(RepartidorPojo repartidorRequest) {
        ArrayList<RepartidorPojo> repartidoresResponse = new ArrayList<>();

        try {
            motor.connect();

            String sql = SQL_SELECT;
            if (repartidorRequest.getId() > 0) {
                sql += " AND id = " + repartidorRequest.getId();
            }
            if (repartidorRequest.getDni() != null) {
                sql += " AND dni LIKE '%" + repartidorRequest.getDni() + "%'";
            }
            if (repartidorRequest.getNombre() != null) {
                sql += " AND nombre = '" + repartidorRequest.getNombre() + "'";
            }
            if (repartidorRequest.getEmail() != null) {
                sql += " AND email = '" + repartidorRequest.getEmail() + "'";
            }

            ResultSet rs = motor.executeQuery(sql);
            while (rs.next()) {
                RepartidorPojo repartidorResponse = new RepartidorPojo();
                repartidorResponse.setId(rs.getInt(1));
                repartidorResponse.setDni(rs.getString(2));
                repartidorResponse.setNombre(rs.getString(3));
                repartidorResponse.setEmail(rs.getString(4));

                repartidoresResponse.add(repartidorResponse);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            motor.disconnect();
        }

        return repartidoresResponse;
    }

    public ArrayList<GeolocalizacionPojo> getHistory(GeolocalizacionPojo geolocalizacionRequest) {
        ArrayList<GeolocalizacionPojo> geolocalizacionResponse = new ArrayList<>();

        try {
            motor.connect();

            String sql = SQL_SELECT_HISTORY;
            if (geolocalizacionRequest.getId() > 0) {
                sql += " AND id = " + geolocalizacionRequest.getId();
            }
            if (geolocalizacionRequest.getIdRepartidor() > 0) {
                sql += " AND id_repartidor = " + geolocalizacionRequest.getIdRepartidor();
            }
            if (geolocalizacionRequest.getCordLat() != null) {
                sql += " AND cord_lat LIKE '%" + geolocalizacionRequest.getCordLat() + "%'";
            }
            if (geolocalizacionRequest.getCordLat() != null) {
                sql += " AND cord_lng LIKE '%" + geolocalizacionRequest.getCordLng() + "%'";
            }
            if (geolocalizacionRequest.getFecha() != null) {
                sql += " AND fecha = '" + geolocalizacionRequest.getFecha() + "'";
            }

            ResultSet rs = motor.executeQuery(sql);
            while (rs.next()) {
                GeolocalizacionPojo geolocalizacion = new GeolocalizacionPojo();
                geolocalizacion.setId(rs.getInt(1));
                geolocalizacion.setIdRepartidor(rs.getInt(2));
                geolocalizacion.setCordLat(rs.getString(3));
                geolocalizacion.setCordLng(rs.getString(4));
                geolocalizacion.setFecha(rs.getString(5));

                geolocalizacionResponse.add(geolocalizacion);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            motor.disconnect();
        }

        return geolocalizacionResponse;
    }
}
