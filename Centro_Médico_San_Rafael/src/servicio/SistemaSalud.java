package servicio;

import java.util.ArrayList;
import modelo.Cita;
import modelo.Especialidad;
import modelo.Medico;
import modelo.Paciente;

public class SistemaSalud {
    private static SistemaSalud instancia;

    private ArrayList<Paciente> pacientes = new ArrayList<>();
    private ArrayList<Medico> medicos = new ArrayList<>();
    private ArrayList<Especialidad> especialidades = new ArrayList<>();
    private ArrayList<Cita> citas = new ArrayList<>();

    // NUEVO: contador para que el codigo de una cita nunca se repita,
    // aunque se cancelen citas
    private int contadorCitas = 0;

    private SistemaSalud() { }

    public static SistemaSalud getInstancia() {
        if (instancia == null) {
            instancia = new SistemaSalud();
        }
        return instancia;
    }

    // ---------- Agregar ----------
    public void agregarPaciente(Paciente p) { pacientes.add(p); }
    public void agregarMedico(Medico m) { medicos.add(m); }
    public void agregarEspecialidad(Especialidad e) { especialidades.add(e); }
    public void agregarCita(Cita c) { citas.add(c); }

    // ---------- Eliminar (NUEVO) ----------
    public void eliminarPaciente(Paciente p) { pacientes.remove(p); }
    public void eliminarMedico(Medico m) { medicos.remove(m); }
    public void eliminarEspecialidad(Especialidad e) { especialidades.remove(e); }
    public void eliminarCita(Cita c) { citas.remove(c); }

    // ---------- Buscar ----------
    public Paciente buscarPaciente(String codigo) {
        for (Paciente p : pacientes) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Paciente buscarPacientePorDni(String dni) {
        for (Paciente p : pacientes) {
            if (p.getDni().equals(dni)) {
                return p;
            }
        }
        return null;
    }

    public Medico buscarMedico(String codigo) {
        for (Medico m : medicos) {
            if (m.getCodigo().equals(codigo)) {
                return m;
            }
        }
        return null;
    }

    public Especialidad buscarEspecialidad(String nombre) {
        for (Especialidad e : especialidades) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }

    public Cita buscarCita(String codigo) {
        for (Cita c : citas) {
            if (c.getCodigo().equals(codigo)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Cita> buscarCitasPorDni(String dni) {
        ArrayList<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getPaciente().getDni().equals(dni)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    // ---------- Reglas del sistema (NUEVO) ----------
    public String generarCodigoCita() {
        contadorCitas++;
        return "C" + contadorCitas;
    }

    public boolean pacienteTieneCitas(Paciente p) {
        for (Cita c : citas) {
            if (c.getPaciente() == p) {
                return true;
            }
        }
        return false;
    }

    public boolean medicoTieneCitas(Medico m) {
        for (Cita c : citas) {
            if (c.getMedico() == m) {
                return true;
            }
        }
        return false;
    }

    // true si el medico ya tiene una cita en esa fecha y hora
    public boolean existeCitaMedico(Medico m, String fecha, String hora) {
        for (Cita c : citas) {
            if (c.getMedico() == m && c.getFecha().equals(fecha) && c.getHora().equals(hora)) {
                return true;
            }
        }
        return false;
    }

    public int contarMedicosPorEspecialidad(Especialidad e) {
        int total = 0;
        for (Medico m : medicos) {
            if (m.getEspecialidad() == e) {
                total++;
            }
        }
        return total;
    }

    // ---------- Getters ----------
    public ArrayList<Paciente> getPacientes() { return pacientes; }
    public ArrayList<Medico> getMedicos() { return medicos; }
    public ArrayList<Especialidad> getEspecialidades() { return especialidades; }
    public ArrayList<Cita> getCitas() { return citas; }
}


