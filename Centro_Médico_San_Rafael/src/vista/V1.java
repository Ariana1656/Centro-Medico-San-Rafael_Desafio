package vista;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Modelo.cita;
import Modelo.medico;
import Modelo.paciente;
import servicio.FabricaPersonas;
import servicio.ProcesadorPacientes;
import servicio.SistemaSalud;

public class V1	 extends JFrame {
    private SistemaSalud sistema = SistemaSalud.getInstancia();

    // Pestaña pacientes
    private JTextField txtCodigo = new JTextField(8);
    private JTextField txtDni = new JTextField(8);
    private JTextField txtNombre = new JTextField(10);
    private JTextField txtEdad = new JTextField(4);
    private JTextField txtTelefono = new JTextField(9);
    private JTextField txtBuscar = new JTextField(10);
    private DefaultTableModel modeloTabla = new DefaultTableModel(
            new String[]{"Código", "DNI", "Nombre", "Edad", "Teléfono"}, 0);

    // Pestaña citas
    private JTextField txtDniCita = new JTextField(8);
    private JComboBox<medico> cmbMedicos = new JComboBox<>();
    private JTextField txtFecha = new JTextField(8);
    private JTextField txtHora = new JTextField(5);
    private JTextField txtMotivo = new JTextField(15);
    private JTextArea areaCitas = new JTextArea(10, 40);

    // Pestaña reporte
    private JTextArea areaReporte = new JTextArea(15, 40);

    public V1() {
        setTitle("Centro Médico San Rafael");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cargarMedicosEnCombo();

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Pacientes", crearPanelPacientes());
        pestanas.addTab("Citas", crearPanelCitas());
        pestanas.addTab("Reporte", crearPanelReporte());
        add(pestanas);
    }

    // ---------- Paneles ----------
    private JPanel crearPanelPacientes() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Código")); panel.add(txtCodigo);
        panel.add(new JLabel("DNI")); panel.add(txtDni);
        panel.add(new JLabel("Nombre")); panel.add(txtNombre);
        panel.add(new JLabel("Edad")); panel.add(txtEdad);
        panel.add(new JLabel("Teléfono")); panel.add(txtTelefono);

        JButton btnRegistrar = new JButton("Registrar Paciente");
        panel.add(btnRegistrar);

        panel.add(new JLabel("Buscar (código o DNI)")); panel.add(txtBuscar);
        JButton btnBuscar = new JButton("Buscar");
        panel.add(btnBuscar);

        panel.add(new JScrollPane(new JTable(modeloTabla)));

        btnRegistrar.addActionListener(e -> registrarPaciente());
        btnBuscar.addActionListener(e -> buscarPaciente());
        return panel;
    }

    private JPanel crearPanelCitas() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("DNI paciente")); panel.add(txtDniCita);
        panel.add(new JLabel("Médico")); panel.add(cmbMedicos);
        panel.add(new JLabel("Fecha")); panel.add(txtFecha);
        panel.add(new JLabel("Hora")); panel.add(txtHora);
        panel.add(new JLabel("Motivo")); panel.add(txtMotivo);

        JButton btnCita = new JButton("Registrar Cita");
        JButton btnConsultar = new JButton("Consultar citas del DNI");
        panel.add(btnCita);
        panel.add(btnConsultar);

        areaCitas.setEditable(false);
        panel.add(new JScrollPane(areaCitas));

        btnCita.addActionListener(e -> registrarCita());
        btnConsultar.addActionListener(e -> consultarCitas());
        return panel;
    }

    private JPanel crearPanelReporte() {
        JPanel panel = new JPanel();
        JButton btnReporte = new JButton("Generar Reporte");
        panel.add(btnReporte);

        areaReporte.setEditable(false);
        panel.add(new JScrollPane(areaReporte));

        btnReporte.addActionListener(e -> generarReporte());
        return panel;
    }

    // ---------- Acciones ----------
    private void cargarMedicosEnCombo() {
        for (medico m : sistema.getMedicos()) {
            cmbMedicos.addItem(m);
        }
    }

    private void registrarPaciente() {
        try {
            String codigo = txtCodigo.getText().trim();
            String dni = txtDni.getText().trim();
            String nombre = txtNombre.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (codigo.isEmpty() || dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }
            if (dni.length() != 8) {
                JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos.");
                return;
            }
            int edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad < 0 || edad > 120) {
                JOptionPane.showMessageDialog(this, "Edad no válida.");
                return;
            }
            if (sistema.buscarPaciente(codigo) != null
                    || sistema.buscarPacientePorDni(dni) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un paciente con ese código o DNI.");
                return;
            }

            paciente p = FabricaPersonas.crearPaciente(codigo, dni, nombre, edad, telefono);
            sistema.agregarPaciente(p);
            modeloTabla.addRow(new Object[]{codigo, dni, nombre, edad, telefono});

            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");
            txtCodigo.setText("");
            txtDni.setText("");
            txtNombre.setText("");
            txtEdad.setText("");
            txtTelefono.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número.");
        }
    }

    private void buscarPaciente() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un código o DNI.");
            return;
        }
        paciente p = sistema.buscarPaciente(texto);
        if (p == null) {
            p = sistema.buscarPacientePorDni(texto);
        }
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el paciente.");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Código: " + p.getCodigo() + "\nDNI: " + p.getDni()
                  + "\nNombre: " + p.getNombre() + "\nEdad: " + p.getEdad()
                  + "\nTeléfono: " + p.getTelefono());
        }
    }

    private void registrarCita() {
        String dni = txtDniCita.getText().trim();
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();
        String motivo = txtMotivo.getText().trim();
        medico medico = (medico) cmbMedicos.getSelectedItem();

        if (dni.isEmpty() || fecha.isEmpty() || hora.isEmpty() || motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos de la cita.");
            return;
        }
        if (medico == null) {
            JOptionPane.showMessageDialog(this, "No hay médicos cargados.");
            return;
        }
        paciente paciente = sistema.buscarPacientePorDni(dni);
        if (paciente == null) {
            JOptionPane.showMessageDialog(this, "No existe un paciente con ese DNI.");
            return;
        }

        String codigoCita = "C" + (sistema.getCitas().size() + 1);
        cita cita = new cita(codigoCita, paciente, medico, fecha, hora, motivo);
        sistema.agregarCita(cita);
        JOptionPane.showMessageDialog(this, "Cita registrada correctamente.");
    }

    private void consultarCitas() {
        String dni = txtDniCita.getText().trim();
        ArrayList<cita> lista = sistema.buscarCitasPorDni(dni);
        areaCitas.setText("");
        if (lista.isEmpty()) {
            areaCitas.setText("No hay citas para ese DNI.");
            return;
        }
        for (cita c : lista) {
            areaCitas.append(c.toString() + "\n");
        }
    }

    private void generarReporte() {
        ArrayList<paciente> pacientes = sistema.getPacientes();
        areaReporte.setText("");

        if (pacientes.isEmpty()) {
            areaReporte.setText("No hay pacientes registrados.");
            return;
        }

        for (paciente p : pacientes) {
            areaReporte.append(p.getNombre() + " | DNI: "
                    + ProcesadorPacientes.ocultarDni(p.getDni()) + "\n");
            ArrayList<cita> citas = sistema.buscarCitasPorDni(p.getDni());
            for (cita c : citas) {
                areaReporte.append("    - " + c.toString() + "\n");
            }
        }

        areaReporte.append("\n--- Resumen (Streams) ---\n");
        areaReporte.append("Mayores de edad: "
                + ProcesadorPacientes.filtrarMayoresDeEdad(pacientes).size() + "\n");
        areaReporte.append("Nombres: "
                + ProcesadorPacientes.obtenerNombres(pacientes) + "\n");
        areaReporte.append("Suma de edades: "
                + ProcesadorPacientes.sumarEdades(pacientes) + "\n");
    }

    // ---------- Inicio del programa ----------
    public static void main(String[] args) {
        SistemaSalud sistema = SistemaSalud.getInstancia();
        sistema.agregarMedico(FabricaPersonas.crearMedico("M1", "Dra. Ana Torres", "Medicina General"));
        sistema.agregarMedico(FabricaPersonas.crearMedico("M2", "Dr. Luis Ramos", "Pediatría"));
        sistema.agregarMedico(FabricaPersonas.crearMedico("M3", "Dra. Rosa Díaz", "Ginecología"));

        new V1().setVisible(true);
    }
}