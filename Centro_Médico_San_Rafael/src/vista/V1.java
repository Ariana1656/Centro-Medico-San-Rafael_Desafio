package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import modelo.Cita;
import modelo.Especialidad;
import modelo.Medico;
import modelo.Paciente;
import servicio.FabricaPersonas;
import servicio.ProcesadorPacientes;
import servicio.SistemaSalud;
import servicio.Validador;

public class V1 extends JFrame {
    private SistemaSalud sistema = SistemaSalud.getInstancia();

    // Pestaña pacientes
    private JTextField txtCodigo = new JTextField(8);
    private JTextField txtDni = new JTextField(8);
    private JTextField txtNombre = new JTextField(10);
    private JTextField txtEdad = new JTextField(4);
    private JTextField txtTelefono = new JTextField(9);
    private JTextField txtBuscar = new JTextField(10);
    private DefaultTableModel modeloPacientes = new DefaultTableModel(
            new String[]{"Código", "DNI", "Nombre", "Edad", "Teléfono"}, 0);
    private JTable tablaPacientes = new JTable(modeloPacientes);

    // Pestaña médicos
    private JTextField txtCodigoMedico = new JTextField(8);
    private JTextField txtNombreMedico = new JTextField(15);
    private JComboBox<Especialidad> cmbEspecialidades = new JComboBox<>();
    private DefaultTableModel modeloMedicos = new DefaultTableModel(
            new String[]{"Código", "Nombre", "Especialidad"}, 0);
    private JTable tablaMedicos = new JTable(modeloMedicos);

    // Pestaña especialidades
    private JTextField txtNombreEspecialidad = new JTextField(20);
    private DefaultTableModel modeloEspecialidades = new DefaultTableModel(
            new String[]{"Nombre", "Médicos"}, 0);
    private JTable tablaEspecialidades = new JTable(modeloEspecialidades);

    // Pestaña citas
    private JTextField txtDniCita = new JTextField(8);
    private JComboBox<Medico> cmbMedicos = new JComboBox<>();
    private JTextField txtFecha = new JTextField(10);
    private JTextField txtHora = new JTextField(5);
    private JTextField txtMotivo = new JTextField(15);
    private DefaultTableModel modeloCitas = new DefaultTableModel(
            new String[]{"Código", "Paciente", "DNI", "Médico", "Fecha", "Hora", "Motivo"}, 0);
    private JTable tablaCitas = new JTable(modeloCitas);

    // Pestaña reporte
    private JTextArea areaReporte = new JTextArea(15, 40);

    public V1() {
        setTitle("Centro Médico San Rafael");
        setSize(850, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        prepararTabla(tablaPacientes);
        prepararTabla(tablaMedicos);
        prepararTabla(tablaEspecialidades);
        prepararTabla(tablaCitas);
        actualizarVistas();

        JTabbedPane pestanas = new JTabbedPane();
        pestanas.addTab("Pacientes", crearPanelPacientes());
        pestanas.addTab("Médicos", crearPanelMedicos());
        pestanas.addTab("Especialidades", crearPanelEspecialidades());
        pestanas.addTab("Citas", crearPanelCitas());
        pestanas.addTab("Reporte", crearPanelReporte());
        add(pestanas);
    }

    // Una sola fila seleccionable y celdas que no se editan a mano
    private void prepararTabla(JTable tabla) {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setDefaultEditor(Object.class, null);
    }

    // ---------- Paneles ----------
    private JPanel crearPanelPacientes() {
        JPanel fila1 = new JPanel();
        fila1.add(new JLabel("Código")); fila1.add(txtCodigo);
        fila1.add(new JLabel("DNI")); fila1.add(txtDni);
        fila1.add(new JLabel("Nombre")); fila1.add(txtNombre);
        fila1.add(new JLabel("Edad")); fila1.add(txtEdad);
        fila1.add(new JLabel("Teléfono")); fila1.add(txtTelefono);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JPanel fila2 = new JPanel();
        fila2.add(btnRegistrar);
        fila2.add(btnGuardar);
        fila2.add(btnEliminar);
        fila2.add(btnLimpiar);

        JButton btnBuscar = new JButton("Buscar");
        JPanel fila3 = new JPanel();
        fila3.add(new JLabel("Buscar (código o DNI)")); fila3.add(txtBuscar);
        fila3.add(btnBuscar);

        JPanel norte = new JPanel(new GridLayout(3, 1));
        norte.add(fila1);
        norte.add(fila2);
        norte.add(fila3);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaPacientes), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrarPaciente());
        btnGuardar.addActionListener(e -> guardarCambiosPaciente());
        btnEliminar.addActionListener(e -> eliminarPaciente());
        btnLimpiar.addActionListener(e -> limpiarCamposPaciente());
        btnBuscar.addActionListener(e -> buscarPaciente());
        tablaPacientes.getSelectionModel().addListSelectionListener(e -> cargarPacienteSeleccionado());
        return panel;
    }

    private JPanel crearPanelMedicos() {
        JPanel fila1 = new JPanel();
        fila1.add(new JLabel("Código")); fila1.add(txtCodigoMedico);
        fila1.add(new JLabel("Nombre")); fila1.add(txtNombreMedico);
        fila1.add(new JLabel("Especialidad")); fila1.add(cmbEspecialidades);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JPanel fila2 = new JPanel();
        fila2.add(btnRegistrar);
        fila2.add(btnGuardar);
        fila2.add(btnEliminar);
        fila2.add(btnLimpiar);

        JPanel norte = new JPanel(new GridLayout(2, 1));
        norte.add(fila1);
        norte.add(fila2);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaMedicos), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrarMedico());
        btnGuardar.addActionListener(e -> guardarCambiosMedico());
        btnEliminar.addActionListener(e -> eliminarMedico());
        btnLimpiar.addActionListener(e -> limpiarCamposMedico());
        tablaMedicos.getSelectionModel().addListSelectionListener(e -> cargarMedicoSeleccionado());
        return panel;
    }

    private JPanel crearPanelEspecialidades() {
        JPanel fila1 = new JPanel();
        fila1.add(new JLabel("Nombre de la especialidad")); fila1.add(txtNombreEspecialidad);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnGuardar = new JButton("Guardar cambios");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        JPanel fila2 = new JPanel();
        fila2.add(btnRegistrar);
        fila2.add(btnGuardar);
        fila2.add(btnEliminar);
        fila2.add(btnLimpiar);

        JPanel norte = new JPanel(new GridLayout(2, 1));
        norte.add(fila1);
        norte.add(fila2);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaEspecialidades), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrarEspecialidad());
        btnGuardar.addActionListener(e -> guardarCambiosEspecialidad());
        btnEliminar.addActionListener(e -> eliminarEspecialidad());
        btnLimpiar.addActionListener(e -> limpiarCamposEspecialidad());
        tablaEspecialidades.getSelectionModel().addListSelectionListener(e -> cargarEspecialidadSeleccionada());
        return panel;
    }

    private JPanel crearPanelCitas() {
        JPanel fila1 = new JPanel();
        fila1.add(new JLabel("DNI paciente")); fila1.add(txtDniCita);
        fila1.add(new JLabel("Médico")); fila1.add(cmbMedicos);

        JPanel fila2 = new JPanel();
        fila2.add(new JLabel("Fecha (dd/MM/yyyy)")); fila2.add(txtFecha);
        fila2.add(new JLabel("Hora (HH:mm)")); fila2.add(txtHora);
        fila2.add(new JLabel("Motivo")); fila2.add(txtMotivo);

        JButton btnCita = new JButton("Registrar Cita");
        JButton btnConsultar = new JButton("Consultar citas del DNI");
        JButton btnTodas = new JButton("Ver todas");
        JButton btnCancelar = new JButton("Cancelar cita");
        JPanel fila3 = new JPanel();
        fila3.add(btnCita);
        fila3.add(btnConsultar);
        fila3.add(btnTodas);
        fila3.add(btnCancelar);

        JPanel norte = new JPanel(new GridLayout(3, 1));
        norte.add(fila1);
        norte.add(fila2);
        norte.add(fila3);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaCitas), BorderLayout.CENTER);

        btnCita.addActionListener(e -> registrarCita());
        btnConsultar.addActionListener(e -> consultarCitas());
        btnTodas.addActionListener(e -> refrescarTablaCitas(sistema.getCitas()));
        btnCancelar.addActionListener(e -> cancelarCita());
        return panel;
    }

    private JPanel crearPanelReporte() {
        JButton btnReporte = new JButton("Generar Reporte");
        JPanel norte = new JPanel();
        norte.add(btnReporte);

        areaReporte.setEditable(false);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaReporte), BorderLayout.CENTER);

        btnReporte.addActionListener(e -> generarReporte());
        return panel;
    }

    // ---------- Refrescar pantallas ----------
    // Se llama despues de registrar, editar o eliminar, para que todas
    // las pestañas muestren los datos actuales
    private void actualizarVistas() {
        cargarEspecialidadesEnCombo();
        cargarMedicosEnCombo();
        refrescarTablaPacientes();
        refrescarTablaMedicos();
        refrescarTablaEspecialidades();
        refrescarTablaCitas(sistema.getCitas());
    }

    private void cargarEspecialidadesEnCombo() {
        cmbEspecialidades.removeAllItems();
        for (Especialidad esp : sistema.getEspecialidades()) {
            cmbEspecialidades.addItem(esp);
        }
    }

    private void cargarMedicosEnCombo() {
        cmbMedicos.removeAllItems();
        for (Medico m : sistema.getMedicos()) {
            cmbMedicos.addItem(m);
        }
    }

    private void refrescarTablaPacientes() {
        modeloPacientes.setRowCount(0);
        for (Paciente p : sistema.getPacientes()) {
            modeloPacientes.addRow(new Object[]{p.getCodigo(), p.getDni(),
                    p.getNombre(), p.getEdad(), p.getTelefono()});
        }
    }

    private void refrescarTablaMedicos() {
        modeloMedicos.setRowCount(0);
        for (Medico m : sistema.getMedicos()) {
            modeloMedicos.addRow(new Object[]{m.getCodigo(), m.getNombre(),
                    m.getEspecialidad().getNombre()});
        }
    }

    private void refrescarTablaEspecialidades() {
        modeloEspecialidades.setRowCount(0);
        for (Especialidad esp : sistema.getEspecialidades()) {
            modeloEspecialidades.addRow(new Object[]{esp.getNombre(),
                    sistema.contarMedicosPorEspecialidad(esp)});
        }
    }

    // Muestra en la tabla las citas de la lista que recibe (todas o solo las de un DNI)
    private void refrescarTablaCitas(ArrayList<Cita> lista) {
        modeloCitas.setRowCount(0);
        for (Cita c : lista) {
            modeloCitas.addRow(new Object[]{c.getCodigo(),
                    c.getPaciente().getNombre(), c.getPaciente().getDni(),
                    c.getMedico().getNombre(), c.getFecha(), c.getHora(), c.getMotivo()});
        }
    }

    // ================= PACIENTES =================
    private void limpiarCamposPaciente() {
        txtCodigo.setText("");
        txtDni.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        txtCodigo.setEditable(true);
        tablaPacientes.clearSelection();
    }

    // Al hacer clic en una fila, sus datos pasan a los campos para editarlos
    private void cargarPacienteSeleccionado() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            return;
        }
        Paciente p = sistema.buscarPaciente(modeloPacientes.getValueAt(fila, 0).toString());
        if (p == null) {
            return;
        }
        txtCodigo.setText(p.getCodigo());
        txtDni.setText(p.getDni());
        txtNombre.setText(p.getNombre());
        txtEdad.setText(String.valueOf(p.getEdad()));
        txtTelefono.setText(p.getTelefono());
        txtCodigo.setEditable(false); // el código identifica al paciente: no se cambia
    }

    // Devuelve la edad escrita, o -1 si no es válida (ya muestra el mensaje)
    private int leerEdad() {
        try {
            int edad = Integer.parseInt(txtEdad.getText().trim());
            if (edad < 0 || edad > 120) {
                JOptionPane.showMessageDialog(this, "Edad no válida.");
                return -1;
            }
            return edad;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número.");
            return -1;
        }
    }

    // Revisa el formato del DNI y del teléfono (ya muestra el mensaje)
    private boolean formatoPacienteValido(String dni, String telefono) {
        if (dni.length() != 8 || !Validador.soloDigitos(dni)) {
            JOptionPane.showMessageDialog(this, "El DNI debe tener 8 dígitos numéricos.");
            return false;
        }
        if (telefono.length() != 9 || !Validador.soloDigitos(telefono)) {
            JOptionPane.showMessageDialog(this, "El teléfono debe tener 9 dígitos numéricos.");
            return false;
        }
        return true;
    }

    private void registrarPaciente() {
        String codigo = txtCodigo.getText().trim();
        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (codigo.isEmpty() || dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty()
                || txtEdad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }
        if (!formatoPacienteValido(dni, telefono)) {
            return;
        }
        int edad = leerEdad();
        if (edad == -1) {
            return;
        }
        if (sistema.buscarPaciente(codigo) != null
                || sistema.buscarPacientePorDni(dni) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un paciente con ese código o DNI.");
            return;
        }

        Paciente p = FabricaPersonas.crearPaciente(codigo, dni, nombre, edad, telefono);
        sistema.agregarPaciente(p);

        limpiarCamposPaciente();
        actualizarVistas();
        JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");
    }

    private void guardarCambiosPaciente() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la tabla.");
            return;
        }
        Paciente p = sistema.buscarPaciente(modeloPacientes.getValueAt(fila, 0).toString());

        String dni = txtDni.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (dni.isEmpty() || nombre.isEmpty() || telefono.isEmpty()
                || txtEdad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos.");
            return;
        }
        if (!formatoPacienteValido(dni, telefono)) {
            return;
        }
        int edad = leerEdad();
        if (edad == -1) {
            return;
        }
        // el DNI no puede ser el de OTRO paciente
        Paciente otro = sistema.buscarPacientePorDni(dni);
        if (otro != null && otro != p) {
            JOptionPane.showMessageDialog(this, "Ya existe otro paciente con ese DNI.");
            return;
        }

        p.setDni(dni);
        p.setNombre(nombre);
        p.setEdad(edad);
        p.setTelefono(telefono);

        limpiarCamposPaciente();
        actualizarVistas();
        JOptionPane.showMessageDialog(this, "Paciente actualizado correctamente.");
    }

    private void eliminarPaciente() {
        int fila = tablaPacientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un paciente de la tabla.");
            return;
        }
        Paciente p = sistema.buscarPaciente(modeloPacientes.getValueAt(fila, 0).toString());

        if (sistema.pacienteTieneCitas(p)) {
            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar: el paciente tiene citas. Cancela sus citas primero.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar al paciente " + p.getNombre() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            sistema.eliminarPaciente(p);
            limpiarCamposPaciente();
            actualizarVistas();
            JOptionPane.showMessageDialog(this, "Paciente eliminado.");
        }
    }

    private void buscarPaciente() {
        String texto = txtBuscar.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe un código o DNI.");
            return;
        }
        Paciente p = sistema.buscarPaciente(texto);
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

    // ================= MÉDICOS =================
    private void limpiarCamposMedico() {
        txtCodigoMedico.setText("");
        txtNombreMedico.setText("");
        txtCodigoMedico.setEditable(true);
        tablaMedicos.clearSelection();
    }

    private void cargarMedicoSeleccionado() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) {
            return;
        }
        Medico m = sistema.buscarMedico(modeloMedicos.getValueAt(fila, 0).toString());
        if (m == null) {
            return;
        }
        txtCodigoMedico.setText(m.getCodigo());
        txtNombreMedico.setText(m.getNombre());
        cmbEspecialidades.setSelectedItem(m.getEspecialidad());
        txtCodigoMedico.setEditable(false);
    }

    private void registrarMedico() {
        String codigo = txtCodigoMedico.getText().trim();
        String nombre = txtNombreMedico.getText().trim();
        Especialidad esp = (Especialidad) cmbEspecialidades.getSelectedItem();

        if (codigo.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa el código y el nombre del médico.");
            return;
        }
        if (esp == null) {
            JOptionPane.showMessageDialog(this, "Primero registra una especialidad.");
            return;
        }
        if (sistema.buscarMedico(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un médico con ese código.");
            return;
        }

        Medico m = FabricaPersonas.crearMedico(codigo, nombre, esp);
        sistema.agregarMedico(m);

        limpiarCamposMedico();
        actualizarVistas();
        JOptionPane.showMessageDialog(this, "Médico registrado correctamente.");
    }

    private void guardarCambiosMedico() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un médico de la tabla.");
            return;
        }
        Medico m = sistema.buscarMedico(modeloMedicos.getValueAt(fila, 0).toString());

        String nombre = txtNombreMedico.getText().trim();
        Especialidad esp = (Especialidad) cmbEspecialidades.getSelectedItem();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre del médico.");
            return;
        }
        if (esp == null) {
            JOptionPane.showMessageDialog(this, "Elige una especialidad.");
            return;
        }

        m.setNombre(nombre);
        m.setEspecialidad(esp);

        limpiarCamposMedico();
        actualizarVistas();
        JOptionPane.showMessageDialog(this, "Médico actualizado correctamente.");
    }

    private void eliminarMedico() {
        int fila = tablaMedicos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un médico de la tabla.");
            return;
        }
        Medico m = sistema.buscarMedico(modeloMedicos.getValueAt(fila, 0).toString());

        if (sistema.medicoTieneCitas(m)) {
            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar: el médico tiene citas. Cancela sus citas primero.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar al médico " + m.getNombre() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            sistema.eliminarMedico(m);
            limpiarCamposMedico();
            actualizarVistas();
            JOptionPane.showMessageDialog(this, "Médico eliminado.");
        }
    }

    // ================= ESPECIALIDADES =================
    private void limpiarCamposEspecialidad() {
        txtNombreEspecialidad.setText("");
        tablaEspecialidades.clearSelection();
    }

    private void cargarEspecialidadSeleccionada() {
        int fila = tablaEspecialidades.getSelectedRow();
        if (fila == -1) {
            return;
        }
        txtNombreEspecialidad.setText(modeloEspecialidades.getValueAt(fila, 0).toString());
    }

    private void registrarEspecialidad() {
        String nombre = txtNombreEspecialidad.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre de la especialidad.");
            return;
        }
        if (sistema.buscarEspecialidad(nombre) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe esa especialidad.");
            return;
        }

        sistema.agregarEspecialidad(new Especialidad(nombre));

        limpiarCamposEspecialidad();
        actualizarVistas();
        JOptionPane.showMessageDialog(this, "Especialidad registrada correctamente.");
    }

    private void guardarCambiosEspecialidad() {
        int fila = tablaEspecialidades.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una especialidad de la tabla.");
            return;
        }
        Especialidad esp = sistema.buscarEspecialidad(modeloEspecialidades.getValueAt(fila, 0).toString());

        String nuevoNombre = txtNombreEspecialidad.getText().trim();
        if (nuevoNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el nombre de la especialidad.");
            return;
        }
        Especialidad otra = sistema.buscarEspecialidad(nuevoNombre);
        if (otra != null && otra != esp) {
            JOptionPane.showMessageDialog(this, "Ya existe otra especialidad con ese nombre.");
            return;
        }

        esp.setNombre(nuevoNombre); // los médicos que la usan ven el cambio solos

        limpiarCamposEspecialidad();
        actualizarVistas();
        JOptionPane.showMessageDialog(this, "Especialidad actualizada correctamente.");
    }

    private void eliminarEspecialidad() {
        int fila = tablaEspecialidades.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una especialidad de la tabla.");
            return;
        }
        Especialidad esp = sistema.buscarEspecialidad(modeloEspecialidades.getValueAt(fila, 0).toString());

        if (sistema.contarMedicosPorEspecialidad(esp) > 0) {
            JOptionPane.showMessageDialog(this,
                    "No se puede eliminar: hay médicos con esta especialidad.");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la especialidad " + esp.getNombre() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            sistema.eliminarEspecialidad(esp);
            limpiarCamposEspecialidad();
            actualizarVistas();
            JOptionPane.showMessageDialog(this, "Especialidad eliminada.");
        }
    }

    // ================= CITAS =================
    private void registrarCita() {
        String dni = txtDniCita.getText().trim();
        String fecha = txtFecha.getText().trim();
        String hora = txtHora.getText().trim();
        String motivo = txtMotivo.getText().trim();
        Medico med = (Medico) cmbMedicos.getSelectedItem();

        if (dni.isEmpty() || fecha.isEmpty() || hora.isEmpty() || motivo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos de la cita.");
            return;
        }
        if (med == null) {
            JOptionPane.showMessageDialog(this, "No hay médicos cargados.");
            return;
        }
        if (!Validador.fechaValida(fecha)) {
            JOptionPane.showMessageDialog(this,
                    "La fecha debe tener el formato dd/MM/yyyy (por ejemplo 15/12/2026).");
            return;
        }
        if (!Validador.horaValida(hora)) {
            JOptionPane.showMessageDialog(this,
                    "La hora debe tener el formato HH:mm (por ejemplo 09:30).");
            return;
        }
        Paciente pac = sistema.buscarPacientePorDni(dni);
        if (pac == null) {
            JOptionPane.showMessageDialog(this, "No existe un paciente con ese DNI.");
            return;
        }
        if (sistema.existeCitaMedico(med, fecha, hora)) {
            JOptionPane.showMessageDialog(this, "Ese médico ya tiene una cita en esa fecha y hora.");
            return;
        }

        Cita nueva = new Cita(sistema.generarCodigoCita(), pac, med, fecha, hora, motivo);
        sistema.agregarCita(nueva);

        refrescarTablaCitas(sistema.getCitas());
        txtFecha.setText("");
        txtHora.setText("");
        txtMotivo.setText("");
        JOptionPane.showMessageDialog(this,
                "Cita registrada correctamente. Código: " + nueva.getCodigo());
    }

    private void consultarCitas() {
        String dni = txtDniCita.getText().trim();
        if (dni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe el DNI del paciente.");
            return;
        }
        ArrayList<Cita> lista = sistema.buscarCitasPorDni(dni);
        refrescarTablaCitas(lista);
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay citas para ese DNI.");
        }
    }

    private void cancelarCita() {
        int fila = tablaCitas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita de la tabla.");
            return;
        }
        Cita c = sistema.buscarCita(modeloCitas.getValueAt(fila, 0).toString());

        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Cancelar la cita " + c.getCodigo() + " de " + c.getPaciente().getNombre() + "?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            sistema.eliminarCita(c);
            refrescarTablaCitas(sistema.getCitas());
            JOptionPane.showMessageDialog(this, "Cita cancelada.");
        }
    }

    // ================= REPORTE =================
    private void generarReporte() {
        ArrayList<Paciente> pacientes = sistema.getPacientes();
        areaReporte.setText("");

        if (pacientes.isEmpty()) {
            areaReporte.setText("No hay pacientes registrados.");
            return;
        }

        for (Paciente p : pacientes) {
            areaReporte.append(p.getNombre() + " | DNI: "
                    + ProcesadorPacientes.ocultarDni(p.getDni()) + "\n");
            ArrayList<Cita> citas = sistema.buscarCitasPorDni(p.getDni());
            for (Cita c : citas) {
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

        Especialidad general = new Especialidad("Medicina General");
        Especialidad pediatria = new Especialidad("Pediatría");
        Especialidad ginecologia = new Especialidad("Ginecología");
        sistema.agregarEspecialidad(general);
        sistema.agregarEspecialidad(pediatria);
        sistema.agregarEspecialidad(ginecologia);

        sistema.agregarMedico(FabricaPersonas.crearMedico("M1", "Dra. Ana Torres", general));
        sistema.agregarMedico(FabricaPersonas.crearMedico("M2", "Dr. Luis Ramos", pediatria));
        sistema.agregarMedico(FabricaPersonas.crearMedico("M3", "Dra. Rosa Díaz", ginecologia));

        new V1().setVisible(true);
    }
}


