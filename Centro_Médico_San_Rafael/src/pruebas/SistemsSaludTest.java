package pruebas;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import Modelo.paciente;
import servicio.SistemaSalud;

class SistemaSaludTest {

    @Test
    void registraYBuscaPacientePorCodigo() {
        SistemaSalud sistema = SistemaSalud.getInstancia();

        paciente paciente = new paciente("TEST-01", "12345678", "Paciente de prueba", 20, "999999999");
        sistema.agregarPaciente(paciente);

        assertSame(paciente, sistema.buscarPaciente("TEST-01"));
    }
}
