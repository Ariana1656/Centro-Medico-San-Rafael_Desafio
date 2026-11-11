package Modelo;

public class cita {
    private String codigo;
    private paciente paciente;
    private medico medico;
    private String fecha;
    private String hora;
    private String motivo;

    public cita(String codigo, paciente paciente, medico medico, String fecha, 
    		String hora, String motivo){
        this.codigo = codigo;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.motivo = motivo;
    }

    public String getCodigo() { return codigo; }
    public paciente getPaciente() { return paciente; }
    public medico getMedico() { return medico; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getMotivo() { return motivo; }

    public String toString() {
        return fecha + " " + hora + " | " + medico.getNombre() + " | " + motivo;
    }
}