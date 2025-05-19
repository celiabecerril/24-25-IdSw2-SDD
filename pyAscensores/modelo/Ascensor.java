package pyAscensores.modelo;

import java.util.*;

public class Ascensor {
    private static final int CAPACIDAD = 6;
    private String id;
    private int plantaActual = 0;
    private List<Persona> personas = new ArrayList<>();
    private Queue<Llamada> llamadas = new LinkedList<>();
    private List<Planta> plantas;

    public Ascensor(String id) {
        this.id = id;
    }

    public void asignarPlantas(List<Planta> plantas) {
        this.plantas = plantas;
    }

    public void atenderLlamada(Llamada llamada) {
        llamadas.add(llamada);
    }

    public void mover() {
        bajar();
        recoger();

        if (!personas.isEmpty())
            moverHacia(personas.get(0).getPlantaDestino());
        else if (!llamadas.isEmpty())
            moverHacia(llamadas.peek().getPlantaOrigen());
    }

    private void moverHacia(int destino) {
        plantaActual += Integer.compare(destino, plantaActual);
    }

    private void bajar() {
        personas.removeIf(p -> {
            if (p.getPlantaDestino() == plantaActual) {
                plantas.get(plantaActual + 3).getEnPlanta().add(p);
                return true;
            }
            return false;
        });
    }

    private void recoger() {
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