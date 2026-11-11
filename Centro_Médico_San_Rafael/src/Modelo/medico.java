package Modelo;

public class medico extends persona {
    private String especialidad;

    public medico(String codigo, String nombre, String especialidad) {
        super(codigo, nombre);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() { return especialidad; }

    public String toString() {
        return nombre + " - " + especialidad;
    }
}