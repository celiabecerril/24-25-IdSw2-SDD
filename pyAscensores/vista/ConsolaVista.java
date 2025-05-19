package pyAscensores.vista;

import java.util.List;
import pyAscensores.modelo.*;

public class ConsolaVista {
    public void mostrarEstado(List<Planta> plantas, List<Ascensor> ascensores, Tiempo tiempo) {
        System.out.println(tiempo.darLaHora());
        for (int i = plantas.size() - 1; i >= 0; i--) {
            Planta p = plantas.get(i);
            String linea = LineaVista.formatearLinea(p, ascensores);
            System.out.println(linea);
        }
        System.out.println("       /--------- Ascensores ------/\n");
    }
}
