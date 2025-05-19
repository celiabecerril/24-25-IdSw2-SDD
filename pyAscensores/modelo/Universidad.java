package pyAscensores.modelo;

import java.util.*;

public class Universidad {
    private List<Planta> plantas;
    private List<Ascensor> ascensores;
    private ControlAscensor control;
    private Tiempo tiempo;
    private final Random random = new Random();

    public Universidad(Tiempo tiempo) {
        this.plantas = new ArrayList<>();
        for (int i = -3; i <= 3; i++) {
            plantas.add(new Planta(i));
        }
        this.ascensores = Arrays.asList(
            new Ascensor("A1"),
            new Ascensor("A2"),
            new Ascensor("A3"),
            new Ascensor("A4")
        );
        this.control = new ControlAscensor(ascensores);
        this.tiempo = tiempo;
        asignarPlantas();
    }

    private void asignarPlantas() {
        for (Ascensor a : ascensores) {
            a.asignarPlantas(plantas);
        }
    }

    public void generarLlegadas() {
        if (tiempo.getTiempoActual() % 2 == 0) {
            int cantidad = random.nextInt(4);
            for (int i = 0; i < cantidad; i++) {
                int destino;
                do {
                    destino = random.nextInt(7) - 3;
                } while (destino == 0);

                Persona p = new Persona(destino);
                Planta origen = plantas.get(3); // planta 0 es índice 3
                origen.personaEsperaAscensor(p);
                control.procesarLlamada(p, 0, destino);
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
}