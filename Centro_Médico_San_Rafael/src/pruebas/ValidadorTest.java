package pruebas;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import servicio.Validador;

class ValidadorTest {

    @Test
    void soloDigitosAceptaNumerosYRechazaLetras() {
        assertTrue(Validador.soloDigitos("12345678"));
        assertFalse(Validador.soloDigitos("1234abcd"));
        assertFalse(Validador.soloDigitos(""));
    }

    @Test
    void validaElFormatoDeLaFecha() {
        assertTrue(Validador.fechaValida("15/12/2026"));
        assertFalse(Validador.fechaValida("15-12-2026"));
        assertFalse(Validador.fechaValida("5/12/2026"));
        assertFalse(Validador.fechaValida("31/04/2026"));
        assertFalse(Validador.fechaValida("15/13/2026"));
    }

    @Test
    void validaElFormatoDeLaHora() {
        assertTrue(Validador.horaValida("09:30"));
        assertFalse(Validador.horaValida("9:30"));
        assertFalse(Validador.horaValida("24:00"));
        assertFalse(Validador.horaValida("10:60"));
    }
}