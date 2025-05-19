package pyAscensores.controlador;

import java.util.Comparator;
import java.util.List;

import pyAscensores.modelo.Ascensor;
import pyAscensores.modelo.Llamada;
import pyAscensores.modelo.Persona;

public class ControlAscensor {
    private List<Ascensor> ascensores;

    public ControlAscensor(List<Ascensor> ascensores) {
        this.ascensores = ascensores;
    }

    public void procesarLlamada(Persona p, int origen, int destino) {
        Ascensor elegido = ascensores.stream()
                .min(Comparator.comparingInt(a -> Math.abs(a.getPlantaActual() - origen)))
                .orElse(ascensores.get(0));
        elegido.atenderLlamada(new Llamada(origen, destino, p));
    }

    public void moverAscensores() {
        ascensores.forEach(Ascensor::mover);
    }
}
