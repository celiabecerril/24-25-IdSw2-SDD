package pyAscensores.modelo;

public class Tiempo {
    private int dia;
    private int hora;
    private int minuto;

    public Tiempo(int dia, int hora, int minuto) {
        this.dia = dia;
        this.hora = hora;
        this.minuto = minuto;
    }

    public void avanzarMinuto() {
        minuto++;
        if (minuto >= 60) {
            minuto = 0;
            hora++;
            if (hora >= 24) {
                hora = 0;
                dia++;
            }
        }
    }

    public String darLaHora() {
        return String.format("DÍA: %02d HORA: %02d:%02d", dia, hora, minuto);
    }

    public int getHora() {
        return hora;
    }

    public boolean esHoraEntradaPermitida() {
        return hora >= 9 && hora < 21;
    }
}