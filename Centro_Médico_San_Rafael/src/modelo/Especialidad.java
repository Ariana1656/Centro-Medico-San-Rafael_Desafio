package modelo;

// NUEVA clase (Fase 2): cada medico tiene una especialidad
public class Especialidad {
    private String nombre;

    public Especialidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String toString() {
        return nombre;
    }
}
