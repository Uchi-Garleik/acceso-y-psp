package Profesor;

import Estudiante.Estudiante;
import Examen.Examen;

import java.util.Random;

public class Profesor {
    static boolean examenTerminado = false;
    public static void main(String[] args) {
        Examen examen = new Examen((new Random().nextInt(5)+1)*100);
        for (int i = 0; i < 4; i++) {
             Estudiante estudiante = new Estudiante(i, examen);
             estudiante.start();
        }
        try{
            System.out.println("Tiempo para terminar el examen: " + examen.getTiempo());
            Thread.sleep(examen.getTiempo());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        examenTerminado = true;
        System.out.println("El examen ha terminado: " + examenTerminado);
    }

    public static boolean isExamenTerminado() {
        return examenTerminado;
    }

    public static void setExamenTerminado(boolean examenTerminado) {
        Profesor.examenTerminado = examenTerminado;
    }
}
