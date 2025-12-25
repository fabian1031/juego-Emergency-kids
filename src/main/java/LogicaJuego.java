package main.java;

public class LogicaJuego {

    private int puntos;

    public LogicaJuego() {
        this.puntos = 0;
    }

    public int getPuntos() {
        return puntos;
    }

    public void reiniciarJuego() {
        this.puntos = 0;
    }

    /**
     * Lógica central: Suma o resta puntos y evita negativos.
     */
    public int procesarAccion(boolean esCorrecto, int premio, int castigo) {
        if (esCorrecto) {
            puntos += premio;
        } else {
            puntos -= castigo;
            if (puntos < 0) puntos = 0;
        }
        return puntos;
    }

    /**
     * Verifica si pasaste el nivel (ej: Nivel 1 necesita 2 aciertos).
     */
    public boolean esMetaCumplida(int aciertosActuales, int aciertosNecesarios) {
        return aciertosActuales >= aciertosNecesarios;
    }

    /**
     * Valida el número de emergencia (123).
     */
    public boolean esNumeroEmergenciaValido(String numero) {
        return "123".equals(numero);
    }

    /**
     * Calcula la medalla final.
     */
    public String determinarMedalla() {
        if (puntos > 90) return "ORO";
        if (puntos >= 70) return "PLATA";
        if (puntos >= 50) return "BRONCE";
        return "PARTICIPACION";
    }
}