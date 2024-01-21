package profesor;

import alumno.Alumno;
import examen.Examen;

import java.util.Random;

public class Profesor{
    private static boolean examenTerminado = false;
    public static void main(String[] args) {
        int tiempoTerminarExamen = (new Random().nextInt(6)+1)*100;
        Examen examen = new Examen(tiempoTerminarExamen);
        for (int i = 0; i < 5; i++) {
            Alumno alumno = new Alumno(i+1, examen);
            alumno.start();
        }

        try {
            Thread.sleep(tiempoTerminarExamen+300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        examenTerminado = true;
        System.out.println("Examen ha terminado");
    }

    public static boolean isExamenTerminado() {
        return examenTerminado;
    }

    public static void setExamenTerminado(boolean examenTerminado) {
        Profesor.examenTerminado = examenTerminado;
    }
}
