package pyAscensores.controlador;

import pyAscensores.modelo.Tiempo;
import pyAscensores.modelo.Universidad;
import pyAscensores.vista.ConsolaVista;

public class Mundo {
    private Universidad universidad;
    private Tiempo tiempo;
    private ConsolaVista vista;

    public Mundo() {
        this.tiempo = new Tiempo();
        this.universidad = new Universidad(tiempo);
        this.vista = new ConsolaVista();
    }

    public void simularDia() {
        while (tiempo.avanzarTiempo()) {
            universidad.generarLlegadas();
            universidad.actualizarEstado();
            vista.mostrarEstado(universidad.getPlantas(), universidad.getAscensores(), tiempo);
            try {
                Thread.sleep(500); // Simula medio segundo por minuto
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
