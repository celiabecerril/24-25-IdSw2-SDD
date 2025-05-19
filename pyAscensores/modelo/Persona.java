package pyAscensores.modelo;

public class Persona {
    private int destino;
    private boolean quiereSalir;
    private int tiempoRestante;

    public Persona(int destino) {
        this.destino = destino;
        this.quiereSalir = false;
        this.tiempoRestante = 5 + (int) (Math.random() * 11); // entre 5 y 15 minutos
    }

    public int getPlantaDestino() {
        return destino;
    }

    public void decrementarTiempo() {
        if (tiempoRestante > 0) tiempoRestante--;
        if (tiempoRestante <= 0 && destino != 0) quiereSalir = true;
    }

    public boolean debeSalir() {
        return quiereSalir;
    }

    public void marcarSalida() {
        this.quiereSalir = false;
        this.destino = 0;
        this.tiempoRestante = 0;
    }

    public boolean haSalido() {
        return destino == 0 && tiempoRestante <= 0 && !quiereSalir;
    }
}