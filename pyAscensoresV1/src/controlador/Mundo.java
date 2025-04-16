package controlador;

import modelo.*;
import vista.VistaConsola;

public class Mundo {
    private static final int HORA_INICIO = 8;
    private static final int HORA_CIERRE = 14;
    private static final int PROBABILIDAD_DE_LLEGADA = 60;
    private static final int PLANTA_MINIMA = -3;
    private static final int PLANTA_MAXIMA = 3;
    private static final int RANGO_ALEATORIO = 100;

    private Universidad universidad;
    private Tiempo tiempo;
    private VistaConsola vista;

    public Mundo() {
        tiempo = new Tiempo(HORA_INICIO, 0);
        universidad = new Universidad(tiempo);
        vista = new VistaConsola();
    }

    private boolean esperar() {
        return vista.preguntarContinuar();
    }

    private Persona generarPersona() {
        int destino = (int) (Math.random() * (PLANTA_MAXIMA - PLANTA_MINIMA + 1)) + PLANTA_MINIMA;
        vista.mostrarMensajePersonaGenerada(destino);
        return new Persona(destino);
    }

    private void avanzarMinuto() {
        tiempo.avanzarMinuto();
    }

    private void simular() {
        do {
            int numeroAleatorio = (int) (Math.random() * RANGO_ALEATORIO);
            if (numeroAleatorio < PROBABILIDAD_DE_LLEGADA) {
                Persona nueva = generarPersona();
                universidad.acogerPersona(nueva);
            }
            universidad.simular(vista);
            if (!esperar()) {
                System.exit(0);
            }
            avanzarMinuto();
        } while (tiempo.getHora() < HORA_CIERRE);
    }

    public static void main(String[] args) {
        Mundo mundo = new Mundo();
        mundo.simular();
    }
}