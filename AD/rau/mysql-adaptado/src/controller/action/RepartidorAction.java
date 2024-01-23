package controller.action;

import model.dao.RepartidorDao;
import model.pojo.GeolocalizacionPojo;
import model.pojo.RepartidorPojo;

import java.util.ArrayList;

public class RepartidorAction implements IAction {
    private RepartidorDao repartidorDao;

    public RepartidorAction() {
        this.repartidorDao = new RepartidorDao();
    }

    @Override
    public String execute(String[] request) {
        String ans = null;

        switch (request[1]) {
            case "get":
                ans = this.get(request);
                break;
            case "get-history":
                ans = this.getHistory(request);
        }

        return ans;
    }

    private String get(String[] request) {
        RepartidorPojo repartidorRequest = new RepartidorPojo();
        repartidorRequest.setId(Integer.parseInt(request[2]));

        ArrayList<RepartidorPojo> repartidorResponse = repartidorDao.get(repartidorRequest);

        return RepartidorPojo.toJson(repartidorResponse);
    }

    private String getHistory(String[] request) {
        GeolocalizacionPojo geolocalizacionRequest = new GeolocalizacionPojo();
        geolocalizacionRequest.setIdRepartidor(Integer.parseInt(request[2]));

        ArrayList<GeolocalizacionPojo> geolocalizacionResponse = repartidorDao.getHistory(geolocalizacionRequest);

        return  GeolocalizacionPojo.toJson(geolocalizacionResponse);
    }
}
