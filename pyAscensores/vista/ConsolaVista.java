package pyAscensores.vista;

import java.util.List;
import pyAscensores.modelo.*;

public class ConsolaVista {
    public void mostrarEstado(List<Planta> plantas, List<Ascensor> ascensores, Tiempo tiempo) {
        System.out.println("Tiempo: " + tiempo.getTiempoActual());
        for (int i = plantas.size() - 1; i >= 0; i--) {
            Planta p = plantas.get(i);
            StringBuilder linea = new StringBuilder();
            linea.append(String.format("Planta %2d ", p.getNumero()));
            linea.append(String.format(" %6s ", formatCantidad(p.getEsperando().size())));
            for (Ascensor ascensor : ascensores) {
                if (ascensor.getPlantaActual() == p.getNumero()) {
                    linea.append(String.format(" [%s:%d] ", ascensor.getId(), ascensor.getCantidadPersonas()));
                } else {
                    linea.append("   |   ");
                }
            }
            linea.append(String.format("   __%d__", p.getEnPlanta().size()));
            System.out.println(linea);
        }
        System.out.println("       /--------- Ascensores ------/\n");
    }

    private String formatCantidad(int cantidad) {
        return cantidad > 0 ? "__" + cantidad + "__" : "_____";
    }
}
