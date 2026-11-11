package Modelo;

public class paciente extends persona {
    private String dni;
    private int edad;
    private String telefono;

    public paciente(String codigo, String dni, String nombre, int edad, String telefono) {
        super(codigo, nombre);
        this.dni = dni;
        this.edad = edad;
        this.telefono = telefono;
    }

    public String getDni() { return dni; }
    public int getEdad() { return edad; }
    public String getTelefono() { return telefono; }
}

