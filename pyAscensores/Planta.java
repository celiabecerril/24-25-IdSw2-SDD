package pyAscensores;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Planta {
    private int numero;
    private List<Persona> esperando;
    private List<Persona> enPlanta;

    public Planta(int numero) {
        this.numero = numero;
        this.esperando = new ArrayList<>();
        this.enPlanta = new ArrayList<>();
    }

    public String getPersonasEsperando() {
        return String.valueOf(esperando.size());
    }

    public String getPersonasEnPlanta() {
        return String.valueOf(enPlanta.size());
    }

    public List<Persona> getEsperando() {
        return new ArrayList<>(esperando);
    }

    public List<Persona> getEnPlanta() {
        return new ArrayList<>(enPlanta);
    }

    public void agregarPersonaEsperando(Persona persona) {
        esperando.add(persona);
    }

    public void removerPersonaEsperando(Persona persona) {
        esperando.remove(persona);
    }

    public void agregarPersonaEnPlanta(Persona persona) {
        enPlanta.add(persona);
    }

    public void removerPersonaEnPlanta(Persona persona) {
        enPlanta.remove(persona);
    }
}