package servicio;

import modelo.Especialidad;
import modelo.Medico;
import modelo.Paciente;

public class FabricaPersonas {

    public static Paciente crearPaciente(String codigo, String dni, String nombre,
                                         int edad, String telefono) {
        return new Paciente(codigo, dni, nombre, edad, telefono);
    }

    // CAMBIO (Fase 2): recibe un objeto especialidad en vez de un String
    public static Medico crearMedico(String codigo, String nombre, Especialidad esp) {
        return new Medico(codigo, nombre, esp);
    }
}
