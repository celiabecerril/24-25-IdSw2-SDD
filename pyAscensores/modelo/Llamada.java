package modelo;

import java.util.Objects;

public class Llamada {
    private int origen;
    private int destino;
    private Persona persona;

    public Llamada(int origen, int destino, Persona persona) {
        this.origen = origen;
        this.destino = destino;
        this.persona = persona;
    }

    public int getPlantaOrigen() {
        return origen;
    }

    public int getPlantaDestino() {
        return destino;
    }

    public Persona getPersona() {
        return persona;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Llamada llamada = (Llamada) o;
        return origen == llamada.origen &&
               destino == llamada.destino &&
               Objects.equals(persona, llamada.persona);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origen, destino, persona);
    }
}
