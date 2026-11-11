package servicio;

import Modelo.medico;
import Modelo.paciente;

public class FabricaPersonas {

    public static paciente crearPaciente(String codigo, String dni, String nombre,
                                         int edad, String telefono) {
        return new paciente(codigo, dni, nombre, edad, telefono);
    }

    public static medico crearMedico(String codigo, String nombre, String especialidad) {
        return new medico(codigo, nombre, especialidad);
    }
}