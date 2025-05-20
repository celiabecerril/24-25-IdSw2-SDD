package modelo;

public class Piso {
    public static final int MIN_PISO = -3;
    public static final int MAX_PISO = 3;
    public static final int INGRESO = 0;
    public static final int DESPLAZAMIENTO = -MIN_PISO; 

  
    public static int index(int planta) {
        return planta + DESPLAZAMIENTO;
    }
}