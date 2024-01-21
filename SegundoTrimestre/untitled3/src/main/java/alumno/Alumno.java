package alumno;

import examen.Examen;
import profesor.Profesor;

import java.util.Random;

public class Alumno extends Thread {
    Examen examen;
    int idAlumno;
    public Alumno(int id, Examen examen){
        setExamen(examen);
        setIdAlumno(id);
    }

    @Override
    public void run() {
        int i = 0;
        while (!Profesor.isExamenTerminado() && i < examen.getExamen().length){
            int timeRandomizer = new Random().nextInt(2) + 1;
            int timeToStudy = examen.getExamen()[i];
            switch (timeRandomizer){
                case 1:
                    timeToStudy /= 2;
                    break;

                case 3:
                    timeToStudy *= 2;
                    break;
            }
            try {
                System.out.println(getIdAlumno() + " hace pregunta " + i + " y tardará: " + timeToStudy + "ms");
                sleep(timeToStudy);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }

    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
}
