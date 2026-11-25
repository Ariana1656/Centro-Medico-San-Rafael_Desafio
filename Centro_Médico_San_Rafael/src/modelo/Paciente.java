package modelo;

public class Paciente extends Persona {
    private String dni;
    private int edad;
    private String telefono;

    public Paciente(String codigo, String dni, String nombre, int edad, String telefono) {
        super(codigo, nombre);
        this.dni = dni;
        this.edad = edad;
        this.telefono = telefono;
    }

    public String getDni() { return dni; }
    public int getEdad() { return edad; }
    public String getTelefono() { return telefono; }

    // NUEVO (Fase 2): para poder editar
    public void setDni(String dni) { this.dni = dni; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}

