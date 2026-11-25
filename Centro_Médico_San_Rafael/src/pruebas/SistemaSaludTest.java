package pruebas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.Cita;
import modelo.Especialidad;
import modelo.Persona;
import modelo.Paciente;
import modelo.Medico;

import servicio.SistemaSalud;
import servicio.ProcesadorPacientes;

class SistemaSaludTest {

    // PRUEBA 1: Registrar y buscar un paciente
    @Test
    void registraYBuscaPacientePorCodigo() {

        SistemaSalud sistema = SistemaSalud.getInstancia();

        Paciente p = new Paciente(
            "TEST-01",
            "12345678",
            "Paciente de prueba",
            20,
            "999999999"
        );

        sistema.agregarPaciente(p);

        assertSame(p, sistema.buscarPaciente("TEST-01"));
    }

    // PRUEBA 2: Filtrar pacientes mayores de edad
    @Test
    void filtraPacientesMayoresDeEdad() {

        List<Paciente> pacientes = Arrays.asList(
            new Paciente("P01", "11111111", "Carlos", 25, "900000001"),
            new Paciente("P02", "22222222", "Ana", 16, "900000002"),
            new Paciente("P03", "33333333", "Luis", 30, "900000003")
        );

        List<Paciente> resultado =
            ProcesadorPacientes.filtrarMayoresDeEdad(pacientes);

        assertEquals(2, resultado.size());
    }

    // PRUEBA 3: Obtener nombres y sumar edades
    @Test
    void obtieneNombresYSumaEdades() {

        List<Paciente> pacientes = Arrays.asList(
            new Paciente("P04", "44444444", "Carlos", 20, "900000004"),
            new Paciente("P05", "55555555", "Ana", 25, "900000005")
        );

        List<String> nombres =
            ProcesadorPacientes.obtenerNombres(pacientes);

        int suma = ProcesadorPacientes.sumarEdades(pacientes);

        assertEquals(Arrays.asList("Carlos", "Ana"), nombres);
        assertEquals(45, suma);
    }

    // PRUEBA 4: Utilizar paciente y medico como persona
    // CAMBIO (Fase 2): el medico ahora recibe un objeto especialidad
    @Test
    void permiteUsarPacienteYMedicoComoPersona() {

        Persona p1 = new Paciente(
            "P10",
            "11111111",
            "Carlos",
            25,
            "900000001"
        );

        Persona p2 = new Medico(
            "M10",
            "Ana Torres",
            new Especialidad("Medicina General")
        );

        assertEquals("Carlos", p1.getNombre());
        assertEquals("Ana Torres", p2.getNombre());
    }

    // ---------- NUEVAS pruebas (Fase 2) ----------

    // PRUEBA 5: Editar un paciente con los setters
    @Test
    void editaLosDatosDeUnPaciente() {

        Paciente p = new Paciente("P20", "10101010", "Maria", 30, "911111111");

        p.setNombre("Maria Lopez");
        p.setEdad(31);
        p.setTelefono("922222222");

        assertEquals("Maria Lopez", p.getNombre());
        assertEquals(31, p.getEdad());
        assertEquals("922222222", p.getTelefono());
    }

    // PRUEBA 6: Eliminar un paciente
    @Test
    void eliminaUnPaciente() {

        SistemaSalud sistema = SistemaSalud.getInstancia();
        Paciente p = new Paciente("TEST-02", "87654321", "Paciente a eliminar", 30, "988888888");

        sistema.agregarPaciente(p);
        sistema.eliminarPaciente(p);

        assertNull(sistema.buscarPaciente("TEST-02"));
    }

    // PRUEBA 7: Un paciente y un medico con citas se detectan
    @Test
    void detectaPacienteYMedicoConCitas() {

        SistemaSalud sistema = SistemaSalud.getInstancia();
        Medico m = new Medico("TM-01", "Dr. Prueba", new Especialidad("Prueba"));
        Paciente p = new Paciente("TEST-03", "11223344", "Paciente con cita", 40, "977777777");
        Cita c = new Cita("TC-01", p, m, "15/12/2026", "09:30", "Control");

        assertFalse(sistema.pacienteTieneCitas(p));

        sistema.agregarCita(c);
        assertTrue(sistema.pacienteTieneCitas(p));
        assertTrue(sistema.medicoTieneCitas(m));

        sistema.eliminarCita(c);
        assertFalse(sistema.pacienteTieneCitas(p));
    }

    // PRUEBA 8: Un medico no puede tener dos citas a la misma fecha y hora
    @Test
    void detectaCitaDuplicadaDelMismoMedico() {

        SistemaSalud sistema = SistemaSalud.getInstancia();
        Medico m = new Medico("TM-02", "Dr. Duplicado", new Especialidad("Prueba"));
        Paciente p = new Paciente("TEST-04", "55667788", "Paciente", 22, "966666666");
        Cita c = new Cita("TC-02", p, m, "20/12/2026", "10:00", "Control");

        sistema.agregarCita(c);

        assertTrue(sistema.existeCitaMedico(m, "20/12/2026", "10:00"));
        assertFalse(sistema.existeCitaMedico(m, "20/12/2026", "11:00"));

        sistema.eliminarCita(c);
    }

    // PRUEBA 9: Los codigos de cita no se repiten
    @Test
    void generaCodigosDeCitaDistintos() {

        SistemaSalud sistema = SistemaSalud.getInstancia();

        String primero = sistema.generarCodigoCita();
        String segundo = sistema.generarCodigoCita();

        assertNotEquals(primero, segundo);
    }

    // PRUEBA 10: Contar medicos por especialidad (se usa para no borrar una especialidad en uso)
    @Test
    void cuentaMedicosPorEspecialidad() {

        SistemaSalud sistema = SistemaSalud.getInstancia();
        Especialidad esp = new Especialidad("Cardiologia de prueba");
        Medico m = new Medico("TM-03", "Dr. Corazon", esp);

        sistema.agregarEspecialidad(esp);
        assertEquals(0, sistema.contarMedicosPorEspecialidad(esp));

        sistema.agregarMedico(m);
        assertEquals(1, sistema.contarMedicosPorEspecialidad(esp));

        sistema.eliminarMedico(m);
        sistema.eliminarEspecialidad(esp);
        assertNull(sistema.buscarEspecialidad("Cardiologia de prueba"));
    }
}
