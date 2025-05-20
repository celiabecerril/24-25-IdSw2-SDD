// File: modelo/Universidad.java
package modelo;

import java.util.*;
import controlador.ControlAscensor;

/**
 * Gestiona la simulación de llegadas y salidas en la universidad.
 */
public class Universidad {
    public static final double PROBABILIDAD_INGRESO = 0.8;
    private static final int PLANTA_MIN = Piso.MIN_PISO;
    private static final int PLANTA_MAX = Piso.MAX_PISO;
    private static final int INDICE_PLANTA_CERO = Piso.index(Piso.INGRESO);

    private List<Planta> plantas;
    private List<Ascensor> ascensores;
    private ControlAscensor control;
    private Tiempo tiempo;
    private Random random = new Random();
    private int totalPersonasIngresadas = 0;

    public Universidad(Tiempo tiempo) {
        this.tiempo = tiempo;
        plantas = new ArrayList<>();
        for (int i = PLANTA_MIN; i <= PLANTA_MAX; i++) {
            plantas.add(new Planta(i));
        }
        ascensores = Arrays.asList(
            new Ascensor("A1"),
            new Ascensor("A2"),
            new Ascensor("A3"),
            new Ascensor("A4")
        );
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
            int destino;
            do {
                destino = random.nextInt(PLANTA_MAX - PLANTA_MIN + 1) + PLANTA_MIN;
            } while (destino == Piso.INGRESO);

            Persona persona = new Persona(destino);
            plantas.get(INDICE_PLANTA_CERO).personaEsperaAscensor(persona);
            control.procesarLlamada(persona, Piso.INGRESO, destino);
            totalPersonasIngresadas++;
        }
    }

    public void actualizarEstancias() {
        for (Planta planta : plantas) {
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
                control.procesarLlamada(p, planta.getNumero(), Piso.INGRESO);
            });
        }
        // Limpieza de quienes ya han salido de planta 0
        Planta p0 = plantas.get(INDICE_PLANTA_CERO);
        p0.getEnPlanta().removeIf(Persona::haSalido);
    }

    public void moverAscensores() {
        control.moverAscensores();
    }


public void actualizarEstado() {
    // 1) Si es la hora de cierre, convierto a TODOS en llamadas al ascensor
    if (tiempo.getHora() == Tiempo.HORA_CIERRE) {
        for (Planta planta : plantas) {
            // a) Personas que están en la planta: marco destino=0 y las pongo en la cola
            List<Persona> enPlanta = new ArrayList<>(planta.getEnPlanta());
            for (Persona p : enPlanta) {
                p.marcarSalida();                            // destino→0
                planta.getEnPlanta().remove(p);
                planta.getEsperando().add(p);
            }
            // b) Personas que ya estaban esperando: actualizo su llamada
            Queue<Persona> cola = planta.getEsperando();
            for (Persona p : new ArrayList<>(cola)) {
                control.procesarLlamada(p, planta.getNumero(), Piso.INGRESO);
                // NO quito de la cola; el ascensor las recogerá y luego se limpiará en bajar()
            }
        }
        // 2) Muevo los ascensores para evacuar de verdad
        control.moverAscensores();
        return;
    }

    // Lógica normal (antes del cierre)…
    for (Planta planta : plantas) {
        if (planta.getNumero() != Piso.INGRESO) {
            List<Persona> copia = new ArrayList<>(planta.getEnPlanta());
            for (Persona p : copia) {
                p.marcarSalida();
                planta.getEnPlanta().remove(p);
                planta.getEsperando().add(p);
                control.procesarLlamada(p, planta.getNumero(), Piso.INGRESO);
            }
        }
    }
    control.moverAscensores();
}


    public boolean todosSeFueron() {
        boolean plantasVacias = plantas.stream()
            .allMatch(p -> p.getCantidadEsperando() == 0 && p.getCantidadEnPlanta() == 0);
        boolean ascensoresVacios = ascensores.stream()
            .allMatch(a -> a.getCantidadPersonas() == 0);
        return plantasVacias && ascensoresVacios;
    }

    public List<Planta> getPlantas() { return plantas; }
    public List<Ascensor> getAscensores() { return ascensores; }
    public int getTotalPersonasIngresadas() { return totalPersonasIngresadas; }
}
