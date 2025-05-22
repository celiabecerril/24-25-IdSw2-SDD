package modelo;

public class Persona {
    public static final int MIN_TIEMPO_ESTANCIA = 5;
    public static final int MAX_TIEMPO_ESTANCIA = 15;

    private int destino;
    private boolean quiereSalir;
    private int tiempoRestante;

    public Persona(int destino) {
        this.destino = destino;
        this.quiereSalir = false;
        this.tiempoRestante = MIN_TIEMPO_ESTANCIA
                + (int) (Math.random() * (MAX_TIEMPO_ESTANCIA - MIN_TIEMPO_ESTANCIA + 1));
    }

    public int getPlantaDestino() {
        return destino;
    }
    public int setPlantaDestino(int destino) {
        this.destino = destino;
        return this.destino;
    }   

    public void decrementarTiempo() {
        if (tiempoRestante > 0)
            tiempoRestante--;
        if (tiempoRestante <= 0 && destino != Piso.INGRESO)
            quiereSalir = true;
    }

    public boolean debeSalir() {
        return quiereSalir;
    }

    public void marcarSalida() {
        this.quiereSalir = false;
        this.destino = Piso.INGRESO;
        this.tiempoRestante = 0;
    }

    public boolean haSalido() {
        return destino == Piso.INGRESO && tiempoRestante <= 0 && !quiereSalir;
    }
}
