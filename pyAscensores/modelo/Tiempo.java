package pyAscensores.modelo;

public class Tiempo {
    private int tiempo;
    private final int DURACION_DIA = 40;

    public Tiempo() {
        this.tiempo = 0;
    }

    public boolean avanzarTiempo() {
        tiempo++;
        return tiempo <= DURACION_DIA;
    }

    public int getTiempoActual() {
        return tiempo;
    }
}