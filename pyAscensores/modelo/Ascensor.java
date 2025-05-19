package pyAscensores.modelo;

import java.util.*;

public class Ascensor {
    private static final int CAPACIDAD = 6;
    private String id;
    private int plantaActual;
    private List<Persona> personas;
    private Queue<Llamada> llamadas;
    private List<Planta> plantas;

    public Ascensor(String id) {
        this.id = id;
        this.plantaActual = 0;
        this.personas = new ArrayList<>();
        this.llamadas = new LinkedList<>();
    }

    public void asignarPlantas(List<Planta> plantas) {
        this.plantas = plantas;
    }

    public void atenderLlamada(Llamada llamada) {
        llamadas.add(llamada);
    }

    public void mover() {
        bajarPersonas();
        recogerPersonas();

        if (!personas.isEmpty()) {
            moverHacia(personas.get(0).getPlantaDestino());
        } else if (!llamadas.isEmpty()) {
            moverHacia(llamadas.peek().getPlantaOrigen());
        }
    }

    private void moverHacia(int destino) {
        if (plantaActual != destino) {
            plantaActual += Integer.compare(destino, plantaActual);
        } else if (!llamadas.isEmpty() && llamadas.peek().getPlantaOrigen() == plantaActual) {
            recogerPersonas();
        }
    }

    private void bajarPersonas() {
        List<Persona> bajan = new ArrayList<>();
        for (Persona p : personas) {
            if (p.getPlantaDestino() == plantaActual) {
                bajan.add(p);
                Planta planta = plantas.get(plantaActual + 3);
                planta.getEnPlanta().add(p);
            }
        }
        personas.removeAll(bajan);
    }

    private void recogerPersonas() {
        Planta planta = plantas.get(plantaActual + 3);
        Iterator<Persona> it = planta.getEsperando().iterator();
        while (it.hasNext() && personas.size() < CAPACIDAD) {
            Persona p = it.next();
            personas.add(p);
            it.remove();
            llamadas.removeIf(l -> l.getPersona() == p);
        }
    }

    public int getPlantaActual() {
        return plantaActual;
    }

    public String getId() {
        return id;
    }

    public int getCantidadPersonas() {
        return personas.size();
    }
}