package vista;

import java.util.List;
import modelo.*;

public class ConsolaVista {
    public void mostrarEstado(List<Planta> plantas, List<Ascensor> ascensores, Tiempo tiempo) {
        if (tiempo.getHora() >= Tiempo.HORA_CIERRE - 1 && tiempo.getHora() < Tiempo.HORA_CIERRE) {
        System.out.println("⚠️  ¡Atención! La universidad cierra a las " 
            + Tiempo.HORA_CIERRE + ":00. Por favor, diríjanse a la planta 0 para salir.");
    }
        System.out.println(tiempo.darLaHora());
        for (int i = plantas.size() - 1; i >= 0; i--) {
            Planta p = plantas.get(i);
            String linea = LineaVista.formatearLinea(p, ascensores);
            System.out.println(linea);
        }
        System.out.print("                   /--------- Ascensores ------/\n");
        for (Ascensor a : ascensores) {
            System.out.printf("%-12s -> Personas transportadas: %d\n", a.getId(), a.getTotalTransportadas());
        }
        System.out.print("----------------------------------------------------------\n");
    }

    public void mostrarTotalesFinales(Universidad universidad) {
        int totalTransportadas = universidad.getAscensores().stream().mapToInt(Ascensor::getTotalTransportadas).sum();
        System.out.printf("%-12s -> Personas ingresadas en el día: %d\n", "REGISTRO", universidad.getTotalPersonasIngresadas());
        System.out.printf("%-12s -> Viajes totales realizados: %d\n", "VIAJES", totalTransportadas);
    }
}
