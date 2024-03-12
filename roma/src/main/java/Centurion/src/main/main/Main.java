package Centurion.src.main.main;

import Centurion.src.main.centurion.Centurion;
import JulioCesar.src.main.juliocesar.JulioCesar;

public class Main {
    public static void main(String[] args) {
        Centurion centurion = new Centurion("localhost", JulioCesar.loadPortFromConfig());
    }
}
