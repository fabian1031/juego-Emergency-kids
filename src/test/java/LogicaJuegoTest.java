package test.java;

import main.java.LogicaJuego;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LogicaJuegoTest {

    private LogicaJuego juego;

    @BeforeEach
    void setUp() {
        juego = new LogicaJuego();
    }

    @Test
    void testPuntosIniciales() {
        assertEquals(0, juego.getPuntos());
    }

    @Test
    void testSumarPuntos() {
        juego.procesarAccion(true, 15, 5);
        assertEquals(15, juego.getPuntos(), "Debería sumar 15 puntos");
    }

    @Test
    void testRestarPuntosNoNegativos() {
        juego.procesarAccion(false, 15, 5); // 0 - 5
        assertEquals(0, juego.getPuntos(), "No debería bajar de 0");
        
        juego.procesarAccion(true, 10, 0); // Tenemos 10
        juego.procesarAccion(false, 0, 5); // 10 - 5
        assertEquals(5, juego.getPuntos());
    }

    @Test
    void testMetasNivel() {
        // Meta: 2 aciertos
        assertFalse(juego.esMetaCumplida(1, 2));
        assertTrue(juego.esMetaCumplida(2, 2));
    }

    @Test
    void testTelefono() {
        assertTrue(juego.esNumeroEmergenciaValido("123"));
        assertFalse(juego.esNumeroEmergenciaValido("911"));
    }

    @Test
    void testMedallas() {
        juego.procesarAccion(true, 95, 0);
        assertEquals("ORO", juego.determinarMedalla());
    }
}