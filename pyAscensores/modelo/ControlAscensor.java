package pyAscensores.modelo;

import java.util.Comparator;
import java.util.List;

public class ControlAscensor {
    private List<Ascensor> ascensores;

    public ControlAscensor(List<Ascensor> ascensores) {
        this.ascensores = ascensores;
    }

    public void procesarLlamada(Persona persona, int origen, int destino) {
        Ascensor mejor = seleccionarMejorAscensor(origen);
        mejor.atenderLlamada(new Llamada(origen, destino, persona));
    }

    private Ascensor seleccionarMejorAscensor(int planta) {
        return ascensores.stream()
                .min(Comparator.comparingInt(a -> Math.abs(a.getPlantaActual() - planta)))
                .orElse(ascensores.get(0));
    }

    public void moverAscensores() {
        for (Ascensor a : ascensores) {
            a.mover();
        }
    }
}
