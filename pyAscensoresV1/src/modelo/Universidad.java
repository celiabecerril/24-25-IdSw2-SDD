package modelo;

import java.util.ArrayList;
import java.util.List;
import vista.VistaConsola;
import controlador.ControlAscensor;

public class Universidad {
    private static final int PLANTA_MINIMA = -3;
    private static final int PLANTA_MAXIMA = 3;
    private static final int HORA_APERTURA = 8;
    private static final int HORA_CIERRE = 21;

    private Tiempo tiempo;
    private List<Planta> plantas;
    private List<Ascensor> ascensores;
    private List<Persona> personas;
    private ControlAscensor control;

    public Universidad(Tiempo tiempo) {
        this.plantas = new ArrayList<>();
        this.ascensores = new ArrayList<>();
        this.personas = new ArrayList<>();
        this.tiempo = tiempo;
        inicializarEdificio();
        this.control = new ControlAscensor(ascensores);
        asignarPlantasAAscensores();
    }

    private void inicializarEdificio() {
        inicializarPlantas();
        inicializarAscensores();
    }

    private void inicializarPlantas() {
        for (int i = PLANTA_MINIMA; i <= PLANTA_MAXIMA; i++) {
            plantas.add(new Planta(i));
        }
    }

    private void inicializarAscensores() {
        ascensores.add(new Ascensor("A"));
        ascensores.add(new Ascensor("B"));
    }

    private void asignarPlantasAAscensores() {
        for (Ascensor ascensor : ascensores) {
            ascensor.asignarPlantas(plantas);
        }
    }

    public boolean estaAbierta() {
        int hora = tiempo.getHora();
        return hora >= HORA_APERTURA && hora < HORA_CIERRE && !tiempo.esFinDeSemana() && !tiempo.esFestivo();
    }

    public void acogerPersona(Persona persona) {
        if (estaAbierta()) {
            personas.add(persona);
            asignarPersonaAPlanta(persona);
            persona.llamarAlAscensor(control);
        }
    }

    private void asignarPersonaAPlanta(Persona persona) {
        for (Planta planta : plantas) {
            if (planta.getNumero() == persona.getPlantaOrigen()) {
                planta.personaEsperaAscensor(persona);
                break;
            }
        }
    }

    public void evolucionDeLaUniversidad() {
        control.moverAscensores();
    }

    public void simular(VistaConsola vista) {
        if (estaAbierta()) {
            evolucionDeLaUniversidad();
            vista.mostrarEstadoUniversidad(tiempo, plantas, ascensores, PLANTA_MINIMA, PLANTA_MAXIMA);
        } else {
            vista.mostrarUniversidadCerrada();
        }
    }
    
    public List<Planta> getPlantas() {
        return plantas;
    }

    public List<Ascensor> getAscensores() {
        return ascensores;
    }

    public Tiempo getTiempo() {
        return tiempo;
    }

    public int getPlantaMinima() {
        return PLANTA_MINIMA;
    }

    public int getPlantaMaxima() {
        return PLANTA_MAXIMA;
    }

    public ControlAscensor getControl() {
        return control;
    }
}