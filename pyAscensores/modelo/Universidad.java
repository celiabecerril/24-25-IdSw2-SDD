package pyAscensores.modelo;

import java.util.*;

import pyAscensores.controlador.ControlAscensor;

public class Universidad {

    private static final double PROBABILIDAD_INGRESO = 0.8;
    private List<Planta> plantas;
    private List<Ascensor> ascensores;
    private ControlAscensor control;
    private Tiempo tiempo;
    private final Random random = new Random();
    private int totalPersonasIngresadas = 0;

    public Universidad(Tiempo tiempo) {
        this.tiempo = tiempo;
        plantas = new ArrayList<>();
        for (int i = -3; i <= 3; i++) {
            plantas.add(new Planta(i));
        }
        ascensores = Arrays.asList(new Ascensor("A1"), new Ascensor("A2"), new Ascensor("A3"), new Ascensor("A4"));
        control = new ControlAscensor(ascensores);
        asignarPlantas();
    }

    private void asignarPlantas() {
        for (Ascensor ascensor : ascensores) {
            ascensor.asignarPlantas(plantas);
        }
    }

    public boolean estaAbierta() {
        return tiempo.esHoraEntradaPermitida();
    }

    public void generarLlegadas() {
        if (random.nextDouble() < PROBABILIDAD_INGRESO) {
            int destino;
            do {
                destino = random.nextInt(7) - 3;
            } while (destino == 0);

            Persona persona = new Persona(destino);
            plantas.get(3).personaEsperaAscensor(persona);
            control.procesarLlamada(persona, 0, destino);
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

            for (Persona persona : paraSalir) {
                persona.marcarSalida();
                planta.getEsperando().add(persona);
                control.procesarLlamada(persona, planta.getNumero(), 0);
            }
        }

        Planta plantaCero = plantas.get(3);
        Iterator<Persona> itCero = plantaCero.getEnPlanta().iterator();
        while (itCero.hasNext()) {
            Persona persona = itCero.next();
            if (persona.haSalido()) {
                itCero.remove();
            }
        }
    }

    public void actualizarEstado() {
        // Garantiza que si alguien está en planta != 0, solicite salida
        for (Planta planta : plantas) {
            if (planta.getNumero() != 0) {
                List<Persona> paraSalir = new ArrayList<>();
                for (Persona p : planta.getEnPlanta()) {
                    if (!p.debeSalir()) {
                        p.marcarSalida();
                        paraSalir.add(p);
                    }
                }
                planta.getEnPlanta().removeAll(paraSalir);
                for (Persona p : paraSalir) {
                    planta.getEsperando().add(p);
                    control.procesarLlamada(p, planta.getNumero(), 0);
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
