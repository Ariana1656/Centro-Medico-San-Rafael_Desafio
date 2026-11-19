package servicio;

import java.util.ArrayList;
import Modelo.cita;
import Modelo.medico;
import Modelo.paciente;

public class SistemaSalud {
    private static SistemaSalud instancia;

    private ArrayList<paciente> pacientes = new ArrayList<>();
    private ArrayList<medico> medicos = new ArrayList<>();
    private ArrayList<cita> citas = new ArrayList<>();

    private SistemaSalud() { }

    public static SistemaSalud getInstancia() {
        if (instancia == null) {
            instancia = new SistemaSalud();
        }
        return instancia;
    }

    public void agregarPaciente(paciente p) { pacientes.add(p); }
    public void agregarMedico(medico m) { medicos.add(m); }
    public void agregarCita(cita c) { citas.add(c); }

    public paciente buscarPaciente(String codigo) {
        for (paciente p : pacientes) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public paciente buscarPacientePorDni(String dni) {
        for (paciente p : pacientes) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<cita> buscarCitasPorDni(String dni) {
        ArrayList<cita> resultado = new ArrayList<>();
        for (cita c : citas) {
            if (c.getPaciente().getDni().equals(dni)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    public ArrayList<paciente> getPacientes() { return pacientes; }
    public ArrayList<medico> getMedicos() { return medicos; }
    public ArrayList<cita> getCitas() { return citas; }
}
