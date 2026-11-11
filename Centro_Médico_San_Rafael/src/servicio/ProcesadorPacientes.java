package servicio;

import java.util.List;
import java.util.stream.Collectors;
import Modelo.paciente;

public class ProcesadorPacientes {

    // filter: pacientes mayores de edad
	
    public static List<paciente> filtrarMayoresDeEdad(List<paciente> pacientes) {
        return pacientes.stream()
                .filter(p -> p.getEdad() >= 18)
                .collect(Collectors.toList());
    }

    // map: solo los nombres
    
    public static List<String> obtenerNombres(List<paciente> pacientes) {
        return pacientes.stream()
                .map(p -> p.getNombre())
                .collect(Collectors.toList());
    }

    // reduce: suma de edades
    public static int sumarEdades(List<paciente> pacientes) {
        return pacientes.stream()
                .map(p -> p.getEdad())
                .reduce(0, (a, b) -> a + b);
    }

    // 60194065 -> 60****65
    public static String ocultarDni(String dni) {
        if (dni.length() < 5) {
            return dni;
        }
        return dni.substring(0, 2) + "****" + dni.substring(dni.length() - 2);
    }
}