package modelo;

import java.util.*;

public class Planta {
    private int numero;
    private Queue<Persona> esperando = new LinkedList<>();
    private List<Persona> enPlanta = new ArrayList<>();

    public Planta(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

    public Queue<Persona> getEsperando() {
        return esperando;
    }

    public List<Persona> getEnPlanta() {
        return enPlanta;
    }

    public void personaEsperaAscensor(Persona p) {
        esperando.add(p);
    }

    public int getCantidadEsperando() {
        return esperando.size();
    }

    public int getCantidadEnPlanta() {
        return enPlanta.size();
    }
}
