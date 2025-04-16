package vista;

import java.util.List;
import java.util.Scanner;
import modelo.*;


public class VistaConsola {
    private Scanner scanner;
    
    public VistaConsola() {
        this.scanner = new Scanner(System.in);
    }
    
    public boolean preguntarContinuar() {
        System.out.println("Quiere continuar? (s/n)");
        String respuesta = scanner.nextLine();
        return respuesta.equalsIgnoreCase("s");
    }
    
    public void mostrarMensajePersonaGenerada(int destino) {
        System.out.println("Persona generada con destino a planta " + destino);
    }
    
    public void mostrarHora(String hora) {
        System.out.println(hora);
    }
    
    public void mostrarEstadoUniversidad(Tiempo tiempo, List<Planta> plantas, 
                                         List<Ascensor> ascensores, int plantaMinima, int plantaMaxima) {
        mostrarCabeceraEstado(tiempo);
        mostrarEstadoPlantas(plantas, ascensores, plantaMinima, plantaMaxima);
        mostrarPieEstado();
    }
    
    private void mostrarCabeceraEstado(Tiempo tiempo) {
        System.out.println(tiempo.darLaHora());
        System.out.println("     Personas                     Personas");
        System.out.println("     esperando                    en la planta\n");
    }
    
    private void mostrarEstadoPlantas(List<Planta> plantas, List<Ascensor> ascensores, 
                                      int plantaMinima, int plantaMaxima) {
        for (int i = plantaMaxima; i >= plantaMinima; i--) {
            mostrarEstadoPlanta(i, plantas, ascensores);
        }
    }
    
    private void mostrarEstadoPlanta(int plantaNumero, List<Planta> plantas, List<Ascensor> ascensores) {
        StringBuilder linea = new StringBuilder();

        int esperando = obtenerCantidadEsperando(plantaNumero, plantas);
        int enPlanta = obtenerCantidadEnPlanta(plantaNumero, plantas);

        String esperaTexto = String.format("   ___%d_ ", esperando);
        linea.append("Planta ").append(String.format("%2d", plantaNumero)).append(esperaTexto);

        agregarEstadoAscensores(linea, plantaNumero, ascensores);

        linea.append("     __").append(enPlanta).append("__");

        System.out.println(linea.toString());
    }
    
    private int obtenerCantidadEsperando(int plantaNumero, List<Planta> plantas) {
        for (Planta planta : plantas) {
            if (planta.getNumero() == plantaNumero) {
                return planta.getCantidadEsperando();
            }
        }
        return 0;
    }
    
    private int obtenerCantidadEnPlanta(int plantaNumero, List<Planta> plantas) {
        for (Planta planta : plantas) {
            if (planta.getNumero() == plantaNumero) {
                return planta.getCantidadEnPlanta();
            }
        }
        return 0;
    }
    
    private void agregarEstadoAscensores(StringBuilder linea, int plantaNumero, List<Ascensor> ascensores) {
        for (Ascensor ascensor : ascensores) {
            if (ascensor.getPlantaActualAsInt() == plantaNumero) {
                linea.append("  [v").append(ascensor.personasEnElAscensor()).append("v]");
            } else {
                linea.append("   | | ");
            }
        }
    }
    
    private void mostrarPieEstado() {
        System.out.println("       /--------- Ascensores ------/");
    }
    
    public void mostrarUniversidadCerrada() {
        System.out.println("La universidad está cerrada.");
    }
    
    public void mostrarEstadoAscensor(String id, int planta, int personas) {
        System.out.println("Ascensor " + id + " en planta " + planta + ", personas: " + personas);
    }
}