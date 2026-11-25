package modelo;

public abstract class Persona {
    protected String codigo;
    protected String nombre;

    public Persona(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    // NUEVO (Fase 2): para poder editar
    public void setNombre(String nombre) { this.nombre = nombre; }
}
