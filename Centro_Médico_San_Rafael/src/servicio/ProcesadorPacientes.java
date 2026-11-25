package servicio;

import java.util.List;
import java.util.stream.Collectors;
import modelo.Paciente;

public class ProcesadorPacientes {

    public static List<Paciente> filtrarMayoresDeEdad(List<Paciente> pacientes) {
        return pacientes.stream()
                .filter(p -> p.getEdad() >= 18)
                .collect(Collectors.toList());
    }

    public static List<String> obtenerNombres(List<Paciente> pacientes) {
        return pacientes.stream()
                .map(p -> p.getNombre())
                .collect(Collectors.toList());
    }

    public static int sumarEdades(List<Paciente> pacientes) {
        return pacientes.stream()
                .map(p -> p.getEdad())
                .reduce(0, (a, b) -> a + b);
    }

    public static String ocultarDni(String dni) {
        if (dni.length() < 5) {
            return dni;
        }
        return dni.substring(0, 2) + "****" + dni.substring(dni.length() - 2);
    }
}

