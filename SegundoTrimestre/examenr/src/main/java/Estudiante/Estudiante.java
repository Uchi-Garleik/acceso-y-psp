package Estudiante;

import Examen.Examen;
import Profesor.Profesor;

import java.util.Random;

public class Estudiante extends Thread {
    private int idEstudiante;
    Examen examen;
    public Estudiante(int id, Examen examen){
        setIdEstudiante(id);
        setExamen(examen);
    }

    @Override
    public void run() {
        int i = 0;
        while (!Profesor.isExamenTerminado() && i < examen.getPreguntas().size()){
            int randomTiempo = new Random().nextInt(3)+1;

            long tiempoNecesarioPregunta = examen.getPreguntas().get(i);
            switch (randomTiempo){
                case 1:
                    tiempoNecesarioPregunta = examen.getPreguntas().get(i) / 2;
                    break;
                case 3:
                    tiempoNecesarioPregunta = Math.round(examen.getPreguntas().get(i) * 3);
                    break;
            }
            System.out.println(getIdEstudiante() + " empieza la pregunta " + i + " y necesita " + tiempoNecesarioPregunta + " milisegundos para hacerla");
            try {
                sleep(tiempoNecesarioPregunta);
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

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
}
