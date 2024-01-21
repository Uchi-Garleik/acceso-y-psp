package Examen;

import java.util.ArrayList;
import java.util.Random;

public class Examen {
    ArrayList<Integer> preguntas;
    public Examen(int t){
        preguntas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            preguntas.add(t);
        }
    }

    public int getTiempo(){
        int t = 0;
        for (int pregunta : preguntas) {
            t += pregunta;
        }
        return t;
    }

    public ArrayList<Integer> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(ArrayList<Integer> preguntas) {
        this.preguntas = preguntas;
    }
}
