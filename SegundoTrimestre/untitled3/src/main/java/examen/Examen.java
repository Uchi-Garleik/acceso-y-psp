package examen;

import java.util.ArrayList;

public class Examen {
    int[] examen = new int[4];
    public Examen(int tiempo){
        for (int i = 0; i < examen.length; i++) {
            examen[i] = tiempo;
        }
    }

    public int[] getExamen() {
        return examen;
    }

    public void setExamen(int[] examen) {
        this.examen = examen;
    }
}
