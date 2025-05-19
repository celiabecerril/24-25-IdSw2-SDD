package pyAscensores.modelo;

import java.util.*;

public class Universidad {
    private List<Planta> plantas;
    private List<Ascensor> ascensores;
    private ControlAscensor control;
    private Tiempo tiempo;
    private final Random random = new Random();

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
        if (random.nextDouble() < 0.8) {
            int destino;
            do {
                destino = random.nextInt(7) - 3;
            } while (destino == 0);

            Persona persona = new Persona(destino);
            plantas.get(3).personaEsperaAscensor(persona); // Planta 0 = índice 3
            control.procesarLlamada(persona, 0, destino);
        }
    }

    public void actualizarEstado() {
        control.moverAscensores();
    }

    public boolean todosSeFueron() {
        return plantas.stream().allMatch(p -> p.getCantidadEsperando() == 0 && p.getCantidadEnPlanta() == 0);
    }

    public List<Planta> getPlantas() {
        return plantas;
    }

    public List<Ascensor> getAscensores() {
        return ascensores;
    }
}
