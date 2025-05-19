package pyAscensores.modelo;

import java.util.*;

public class Planta {
    private int numero;
    private Queue<Persona> esperando;
    private List<Persona> enPlanta;

    public Planta(int numero) {
        this.numero = numero;
        esperando = new LinkedList<>();
        enPlanta = new ArrayList<>();
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
