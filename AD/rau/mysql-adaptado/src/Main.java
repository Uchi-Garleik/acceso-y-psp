import controller.Controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Controller controller = new Controller();
        String[] request = new String[3];
        boolean isWorking = true;

        while (isWorking) {
            System.out.println("Seleccione una opción del menú:");
            System.out.println("[1] Consultar datos de un repartidor");
            System.out.println("[/] Salir.");

            String selectedOption = sc.nextLine();
            switch (selectedOption) {
                case "1":
                    System.out.println("Seleccione una opción:");
                    System.out.println("[1] Datos del repartidor");
                    System.out.println("[2] Historial de ubicación");

                    String selectedImportTable = sc.nextLine();
                    String repartidorId;
                    switch (selectedImportTable) {
                        case "1":
                            System.out.println("Introduzca el ID del repartidor a consultar:");
                            repartidorId = sc.nextLine();

                            // [0] Model, [1] Action, [2] Repartidor (ID)
                            request[0] = "repartidor";
                            request[1] = "get";
                            request[2] = repartidorId;
                            System.out.println(controller.processRequest(request));
                            break;
                        case "2":
                            System.out.println("Introduzca el ID del repartidor a consultar:");
                            repartidorId = sc.nextLine();

                            // [0] Model, [1] Action, [2] Repartidor (ID)
                            request[0] = "repartidor";
                            request[1] = "get-history";
                            request[2] = repartidorId;
                            System.out.println(controller.processRequest(request));
                        default:
                            System.out.println("La opción seleccionada no está disponible.");
                            break;
                    }
                    break;
                default:
                    isWorking = false;
                    System.out.println("Saliendo del programa...");
                    break;
            }
        }
    }
}
