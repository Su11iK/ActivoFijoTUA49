package vista;

import dao.AreaDAO;
import dao.ResguardanteDAO;
import modelo.Area;
import modelo.Resguardante;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;
import ui.components.SearchField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ResguardantesFrame extends JDialog {

    private JTable tabla;
    private DefaultTableModel modelo;

    private List<Resguardante> listaResguardantes;

    public ResguardantesFrame(JFrame parent) {

        super(parent,
                "Resguardantes",
                true);

        setSize(700, 400);

        setLocationRelativeTo(parent);

        setLayout(new BorderLayout());

        // =========================
        // TABLA
        // =========================
        modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                        return false;
                }
        };

        modelo.addColumn("Nombre");
        modelo.addColumn("Puesto");
        modelo.addColumn("Área");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla),
                BorderLayout.CENTER);

        // =========================
        // BOTONES
        // =========================
        RoundedPanel panelBotones = new RoundedPanel();

        panelBotones.setLayout(
                new BoxLayout(
                        panelBotones,
                        BoxLayout.Y_AXIS
                )
        );

        PrimaryButton btnAgregar =
                new PrimaryButton("Agregar");

        PrimaryButton btnEditar =
                new PrimaryButton("Editar");

        PrimaryButton btnAsignar =
                new PrimaryButton("Asignar");

        PrimaryButton btnEliminar =
                new PrimaryButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnEditar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnAsignar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnEliminar);

        add(panelBotones,
                BorderLayout.EAST);

        // =========================
        // EVENTOS
        // =========================
        btnAgregar.addActionListener(e -> agregar());

        btnEditar.addActionListener(e -> editar());

        btnAsignar.addActionListener(e -> asignar());

        btnEliminar.addActionListener(e -> eliminar());

        cargarTabla();
    }

    // =========================
    // CARGAR TABLA
    // =========================
    private void cargarTabla() {

        ResguardanteDAO dao =
                new ResguardanteDAO();

        listaResguardantes =
                dao.listarResguardantes();

        modelo.setRowCount(0);

        for (Resguardante r :
                listaResguardantes) {

            modelo.addRow(new Object[]{
                    r.getNombre(),
                    r.getPuesto(),
                    r.getNombreArea()
            });
        }
    }

    // =========================
    // AGREGAR
    // =========================
    private void agregar() {

        SearchField txtNombre =
                new SearchField();
        txtNombre.setColumns(25);

        SearchField txtPuesto =
                new SearchField();
        txtPuesto.setColumns(25);

        JComboBox<Area> cbAreas =
                new JComboBox<>();

        AreaDAO areaDAO = new AreaDAO();

        List<Area> listaAreas =
                areaDAO.listarAreas();

        for (Area a : listaAreas) {
            cbAreas.addItem(a);
        }

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));

        panel.add(new JLabel("Nombre"));
        panel.add(txtNombre);

        panel.add(new JLabel("Puesto"));
        panel.add(txtPuesto);

        panel.add(new JLabel("Área"));
        panel.add(cbAreas);

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Agregar Resguardante",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        if (txtNombre.getText().trim().isEmpty()
                || txtPuesto.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nombre y puesto son obligatorios"
            );

            return;
        }

        Area areaSeleccionada =
                (Area) cbAreas.getSelectedItem();

        ResguardanteDAO dao =
                new ResguardanteDAO();

        dao.insertarResguardante(
                txtNombre.getText(),
                txtPuesto.getText(),
                areaSeleccionada.getId()
        );

        cargarTabla();
    }

    // =========================
    // EDITAR
    // =========================
    private void editar() {

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un resguardante"
            );

            return;
        }

        Resguardante r =
                listaResguardantes.get(fila);

        SearchField txtNombre =
                new SearchField(r.getNombre());
        txtNombre.setColumns(25);

        SearchField txtPuesto =
                new SearchField(r.getPuesto());
        txtPuesto.setColumns(25);

        JTextArea txtObs =
                new JTextArea();

        RoundedPanel panel =
                new RoundedPanel(new GridLayout(0, 1));

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Puesto:"));
        panel.add(txtPuesto);

        panel.add(new JLabel("Observaciones:"));
        panel.add(new JScrollPane(txtObs));

        String nombreAnterior =
                r.getNombre();

        String puestoAnterior =
                r.getPuesto();

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Editar Resguardante",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        boolean cambioNombre =
                !nombreAnterior.equals(
                        txtNombre.getText()
                );
        
        boolean cambioPuesto =
                !puestoAnterior.equals(
                        txtPuesto.getText()
                );

        if (!cambioNombre && !cambioPuesto) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se registro ningun cambio.",
                        "Sin cambios",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        if (txtNombre.getText().trim().isEmpty()
                || txtPuesto.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nombre y puesto son obligatorios"
            );

            return;
        }

        ResguardanteDAO dao =
                new ResguardanteDAO();

        dao.editarResguardante(
                r.getId(),
                nombreAnterior,
                txtNombre.getText(),
                txtPuesto.getText(),
                txtObs.getText(),
                cambioNombre
        );

        cargarTabla();
    }

    // =========================
    // ASIGNAR
    // =========================
    private void asignar() {

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un resguardante"
            );

            return;
        }

        Resguardante r =
                listaResguardantes.get(fila);

        String areAnterior =
                r.getNombreArea();

        if (areAnterior == null) {
                areAnterior = "<vacío>";
        }

        JComboBox<Area> cbAreas =
                new JComboBox<>();

        JTextArea txtObs =
                new JTextArea();

        AreaDAO areaDAO =
                new AreaDAO();

        List<Area> listaAreas =
                areaDAO.listarAreas();

        Area seleccionada = null;

        for (Area a : listaAreas) {

            cbAreas.addItem(a);

            if (a.getId() == r.getIdArea()) {
                seleccionada = a;
            }
        }

        cbAreas.setSelectedItem(seleccionada);

        RoundedPanel panel =
                new RoundedPanel(new GridLayout(0, 1));

        panel.add(new JLabel("Área:"));
        panel.add(cbAreas);

        panel.add(new JLabel("Observaciones:"));
        panel.add(new JScrollPane(txtObs));

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Asignar Resguardante",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        Area areaSeleccionada =
                (Area) cbAreas.getSelectedItem();

        if (areaSeleccionada == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se registro ninguna asignación.",
                        "Sin asignación",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        ResguardanteDAO dao =
                new ResguardanteDAO();

        String areNuevo = areaSeleccionada.getNombre();
        
        boolean cambioArea =
                !areAnterior.equals(
                        areNuevo
                );

        if (!cambioArea) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se registro ningun cambio.",
                        "Sin cambios",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        dao.asignarResguardante(
                r.getId(),
                areaSeleccionada.getId(),
                txtObs.getText(),
                areAnterior,
                areNuevo
        );

        cargarTabla();
    }

    // =========================
    // ELIMINAR
    // =========================
    private void eliminar() {

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un resguardante"
            );

            return;
        }

        int confirmar =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Eliminar resguardante?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmar != JOptionPane.YES_OPTION) {
            return;
        }

        Resguardante r =
                listaResguardantes.get(fila);

        ResguardanteDAO dao =
                new ResguardanteDAO();

        if(dao.tieneBienesAsignados(r.getId())) {

                JTextArea txtObs = new JTextArea(5,20);

                RoundedPanel panel = new RoundedPanel(new BorderLayout());

                panel.add(
                new JLabel("Observaciones:"),
                BorderLayout.NORTH
                );

                panel.add(
                new JScrollPane(txtObs),
                BorderLayout.CENTER
                );

                int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Existen bienes asignados. ¿Continuar?",
                        JOptionPane.YES_NO_OPTION
                );

                if(opcion != JOptionPane.YES_OPTION)
                        return;

                dao.quitarResguardanteDeBienes(
                        r.getId(),
                        1,
                        txtObs.getText(),
                        r.getNombre()
                );
        }

        dao.eliminarResguardante(r.getId());

        cargarTabla();
    }
}