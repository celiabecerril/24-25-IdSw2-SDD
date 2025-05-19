package pyAscensores.controlador;

import pyAscensores.modelo.Tiempo;
import pyAscensores.modelo.Universidad;
import pyAscensores.vista.ConsolaVista;

public class Mundo {
        private int dia = 1;
    private int hora = 8;
    private int minuto = 30;

    private Universidad universidad;
    private Tiempo tiempo;
    private ConsolaVista vista;

    public Mundo() {
        this.tiempo = new Tiempo(dia, hora, minuto);
        this.universidad = new Universidad(tiempo);
        this.vista = new ConsolaVista();
    }

    public void simularDia() {

        while (true) {
            tiempo.avanzarMinuto();

            if (universidad.estaAbierta()) {
                universidad.generarLlegadas();
                universidad.actualizarEstancias();
            } else {
                universidad.actualizarEstancias();
                System.out.println("No son horas de ir a la universidad... mejor duerme 😴\n");
            }

            universidad.actualizarEstado();
            vista.mostrarEstado(universidad.getPlantas(), universidad.getAscensores(), tiempo);
            if (tiempo.getHora() == 21) {
                vista.mostrarTotalesFinales(universidad);
            }

            try {
                Thread.sleep(200); // Simulación de paso del tiempo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
