package Modelo;

public abstract class persona {
    protected String codigo;
    protected String nombre;

    public persona(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
}