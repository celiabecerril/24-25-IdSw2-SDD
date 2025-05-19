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
        if (random.nextDouble() < 0.3) {
            int destino;
            do {
                destino = random.nextInt(7) - 3;
            } while (destino == 0);

            Persona persona = new Persona(destino);
            plantas.get(3).personaEsperaAscensor(persona);
            control.procesarLlamada(persona, 0, destino);
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

        // Eliminar personas que han llegado a planta 0 y deben desaparecer
        Planta plantaCero = plantas.get(3); // planta 0 en índice 3
        Iterator<Persona> itCero = plantaCero.getEnPlanta().iterator();
        while (itCero.hasNext()) {
            Persona persona = itCero.next();
            if (persona.haSalido()) {
                itCero.remove();
            }
        }
    }

    public void actualizarEstado() {
        control.moverAscensores();
    }

    public List<Planta> getPlantas() {
        return plantas;
    }

    public List<Ascensor> getAscensores() {
        return ascensores;
    }

    public boolean todosSeFueron() {
        boolean plantasVacias = plantas.stream()
                .allMatch(p -> p.getCantidadEsperando() == 0 && p.getCantidadEnPlanta() == 0);
        boolean ascensoresVacios = ascensores.stream()
                .allMatch(a -> a.getCantidadPersonas() == 0);
        return plantasVacias && ascensoresVacios;
    }
}
