package modelo;

import java.util.*;
import controlador.ControlAscensor;

public class Universidad {
    public static final double PROBABILIDAD_INGRESO = 0.8;
    public static final int MIN_PISO = -3;
    public static final int MAX_PISO = 3;
    public static final int INGRESO = 0;

    private Map<Integer, Planta> plantas;
    private List<Ascensor> ascensores;
    private ControlAscensor control;
    private Tiempo tiempo;
    private Random random = new Random();
    private int totalPersonasIngresadas = 0;

    public Universidad(Tiempo tiempo) {
        this.tiempo = tiempo;
        plantas = new HashMap<>();
        for (int i = MIN_PISO; i <= MAX_PISO; i++) {
            plantas.put(i, new Planta(i));
        }
        ascensores = Arrays.asList(
                new Ascensor("A1"),
                new Ascensor("A2"),
                new Ascensor("A3"),
                new Ascensor("A4"));
        control = new ControlAscensor(ascensores);
        asignarPlantas();
    }

    private void asignarPlantas() {
        ascensores.forEach(a -> a.asignarPlantas(plantas));
    }

    public boolean estaAbierta() {
        return tiempo.esHoraEntradaPermitida();
    }

    public void generarLlegadas() {
    if (random.nextDouble() < PROBABILIDAD_INGRESO && estaAbierta()) {
        Persona persona = new Persona();
        plantas.get(INGRESO).personaEsperaAscensor(persona);
        control.procesarLlamada(new Llamada(INGRESO, persona.getPlantaDestino(), persona));
        totalPersonasIngresadas++;
    }
}


    public void actualizarEstancias() {
        for (Planta planta : plantas.values()) {
            List<Persona> paraSalir = new ArrayList<>();
            Iterator<Persona> it = planta.getEnPlanta().iterator();
            while (it.hasNext()) {
                Persona persona = it.next();
                persona.decrementarTiempo();
                if (persona.debeSalir()) {
                    paraSalir.add(persona);
                    it.remove();
                }
            }
            paraSalir.forEach(p -> {
                p.marcarSalida();
                planta.getEsperando().add(p);
                control.procesarLlamada(new Llamada(INGRESO, planta.getNumero(), p));
            });
        }
        plantas.get(INGRESO).getEnPlanta().removeIf(Persona::haSalido);
    }

    public void moverAscensores() {
        control.moverAscensores();
    }

    public void actualizarEstado() {
        if (!estaAbierta()) {
            for (Planta planta : plantas.values()) {
                List<Persona> enPlanta = new ArrayList<>(planta.getEnPlanta());
                for (Persona p : enPlanta) {
                    p.marcarSalida();
                    planta.getEnPlanta().remove(p);
                    planta.getEsperando().add(p);
                }
                for (Persona p : new ArrayList<>(planta.getEsperando())) {
                    control.procesarLlamada(new Llamada(INGRESO, planta.getNumero(), p));
                }
            }
        }
        control.moverAscensores();
    }

    public boolean todosSeFueron() {
        return contarPersonasDentro() == 0;
    }

    public int contarPersonasDentro() {
        return plantas.values().stream()
            .mapToInt(p -> p.getCantidadEsperando() + p.getCantidadEnPlanta())
            .sum();
    }
    public void incrementarTotalPersonasIngresadas() {
        totalPersonasIngresadas++;
    }

    public Map<Integer, Planta> getPlantas() {
        return plantas;
    }

    public List<Ascensor> getAscensores() {
        return ascensores;
    }

    public int getTotalPersonasIngresadas() {
        return totalPersonasIngresadas;
    }

    public int getPersonasDentro() {
        return contarPersonasDentro();
    }

    public ControlAscensor getControlAscensor() {
        return control;
    }
}
