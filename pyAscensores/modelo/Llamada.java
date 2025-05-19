package pyAscensores.modelo;

public class Llamada {
    private int origen;
    private Persona persona;

    public Llamada(int origen, int destino, Persona persona) {
        this.origen = origen;
        this.persona = persona;
    }

    public int getPlantaOrigen() {
        return origen;
    }

    public Persona getPersona() {
        return persona;
    }
}
