package pyAscensores.modelo;

public class Llamada {
    private int plantaOrigen;
    private int plantaDestino;
    private Persona persona;

    public Llamada(int plantaOrigen, int plantaDestino, Persona persona) {
        this.plantaOrigen = plantaOrigen;
        this.plantaDestino = plantaDestino;
        this.persona = persona;
    }

    public int getPlantaOrigen() {
        return plantaOrigen;
    }

    public int getPlantaDestino() {
        return plantaDestino;
    }

    public Persona getPersona() {
        return persona;
    }
}
