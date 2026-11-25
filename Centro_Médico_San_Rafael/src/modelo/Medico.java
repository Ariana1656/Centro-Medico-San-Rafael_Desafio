package modelo;

public class Medico extends Persona {
    // CAMBIO (Fase 2): antes era un String, ahora es un objeto Especialidad
    private Especialidad especialidad;

    public Medico(String codigo, String nombre, Especialidad especialidad) {
        super(codigo, nombre);
        this.especialidad = especialidad;
    }

    public Especialidad getEspecialidad() { return especialidad; }
    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public String toString() {
        return nombre + " - " + especialidad.getNombre();
    }
}
