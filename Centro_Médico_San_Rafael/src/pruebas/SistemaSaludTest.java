
package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import Modelo.persona;
import Modelo.paciente;
import Modelo.medico;

import servicio.SistemaSalud;
import servicio.ProcesadorPacientes;

class SistemaSaludTest {

    // PRUEBA 1: Registrar y buscar un paciente
    @Test
    void registraYBuscaPacientePorCodigo() {

        SistemaSalud sistema = SistemaSalud.getInstancia();

        paciente paciente = new paciente(
            "TEST-01",
            "12345678",
            "Paciente de prueba",
            20,
            "999999999"
        );

        sistema.agregarPaciente(paciente);

        assertSame(paciente, sistema.buscarPaciente("TEST-01"));
    }

    // PRUEBA 2: Filtrar pacientes mayores de edad
    @Test
    void filtraPacientesMayoresDeEdad() {

        List<paciente> pacientes = Arrays.asList(
            new paciente("P01", "11111111", "Carlos", 25, "900000001"),
            new paciente("P02", "22222222", "Ana", 16, "900000002"),
            new paciente("P03", "33333333", "Luis", 30, "900000003")
        );

        List<paciente> resultado =
            ProcesadorPacientes.filtrarMayoresDeEdad(pacientes);

        assertEquals(2, resultado.size());
    }

    // PRUEBA 3: Obtener nombres y sumar edades
    @Test
    void obtieneNombresYSumaEdades() {

        List<paciente> pacientes = Arrays.asList(
            new paciente("P04", "44444444", "Carlos", 20, "900000004"),
            new paciente("P05", "55555555", "Ana", 25, "900000005")
        );

        List<String> nombres =
            ProcesadorPacientes.obtenerNombres(pacientes);

        int suma = ProcesadorPacientes.sumarEdades(pacientes);

        assertEquals(Arrays.asList("Carlos", "Ana"), nombres);
        assertEquals(45, suma);
    }

    // PRUEBA 4: Utilizar paciente y medico como persona
    @Test
    void permiteUsarPacienteYMedicoComoPersona() {

        persona p1 = new paciente(
            "P10",
            "11111111",
            "Carlos",
            25,
            "900000001"
        );

        persona p2 = new medico(
            "M10",
            "Ana Torres",
            "Medicina General"
        );

        assertEquals("Carlos", p1.getNombre());
        assertEquals("Ana Torres", p2.getNombre());
    }
}

