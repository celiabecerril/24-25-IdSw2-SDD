package controlador;

import modelo.Tiempo;
import modelo.Universidad;
import vista.ConsolaVista;

public class Mundo {
    public static final int DIA = 1;
    public static final int HORA_INICIO = 8;
    public static final int MINUTO_INICIO = 50;
    public static final long PAUSA_SIMULACION_MS = 20;

    private Tiempo tiempo;
    private Universidad universidad;
    private ConsolaVista vista;

    public Mundo() {
        this.tiempo = new Tiempo(DIA, HORA_INICIO, MINUTO_INICIO);
        this.universidad = new Universidad(tiempo);
        this.vista = new ConsolaVista();
    }

        public void simularDia() {
        boolean diaTerminado = false;
        while (!diaTerminado) {
            tiempo.avanzarMinuto();
            int minutosActualesSimulados = tiempo.getHora() * 60 + tiempo.getMinuto();
            universidad.getControlAscensor().setMinutosSimulados(minutosActualesSimulados);
    
            if (universidad.estaAbierta()) {
                universidad.generarLlegadas();
            } else {
                vista.mostrarCierre();
            }
            universidad.actualizarEstancias();
            universidad.actualizarEstado();
            vista.mostrarEstado(universidad.getPlantas(), universidad.getAscensores(), tiempo, universidad);
    
            if (tiempo.getHora() == Tiempo.HORA_CIERRE) {
                vista.mostrarTotalesFinales(universidad);
            }
            vista.finalDia();
            pausar();
        }
    }
    
    private void pausar() {
        try {
            Thread.sleep(PAUSA_SIMULACION_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
