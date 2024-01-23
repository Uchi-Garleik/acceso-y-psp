package controller;

import controller.action.RepartidorAction;

public class Controller {
    public String processRequest(String[] request) {
        String ans = null;

        switch (request[0]) {
            case "repartidor":
                ans = new RepartidorAction().execute(request);
                break;
        }

        return ans;
    }
}
