package vista;

import dao.AreaDAO;
import modelo.Area;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AreasFrame extends JDialog {

    private JTable tabla;
    private DefaultTableModel modelo;

    private List<Area> listaAreas;

    public AreasFrame(JFrame parent) {

        super(parent, "Áreas", true);

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // =========================
        // TABLA
        // =========================
        modelo = new DefaultTableModel();

        modelo.addColumn("Nombre Área");

        tabla = new JTable(modelo);

        add(new JScrollPane(tabla),
                BorderLayout.CENTER);

        // =========================
        // BOTONES
        // =========================
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(
            new FlowLayout(FlowLayout.LEFT)
        );

        panelBotones.setLayout(
            new BoxLayout(panelBotones, BoxLayout.Y_AXIS)
        );

        JButton btnAgregar =
                new JButton("Agregar");

        JButton btnEditar =
                new JButton("Editar");

        JButton btnEliminar =
                new JButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnEditar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.EAST);

        // =========================
        // EVENTOS
        // =========================
        btnAgregar.addActionListener(e -> agregar());

        btnEditar.addActionListener(e -> editar());

        btnEliminar.addActionListener(e -> eliminar());

        cargarAreas();
    }

    // =========================
    // CARGAR
    // =========================
    private void cargarAreas() {

        AreaDAO dao = new AreaDAO();

        listaAreas = dao.listarAreas();

        modelo.setRowCount(0);

        for (Area a : listaAreas) {

            modelo.addRow(new Object[]{
                    a.getNombre()
            });
        }
    }

    // =========================
    // AGREGAR
    // =========================
    private void agregar() {

        String nombre = JOptionPane.showInputDialog(
                this,
                "Nombre del área:"
        );

        if (nombre == null) {
                return;
        }

        if (nombre.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre es obligatorios"
            );

            return;
        }

        AreaDAO dao = new AreaDAO();

        dao.insertarArea(nombre);

        cargarAreas();
    }

    // =========================
    // EDITAR
    // =========================
    private void editar() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(this,
                    "Seleccione un área");

            return;
        }

        Area area = listaAreas.get(fila);

        JTextField txtNombre =
                new JTextField(area.getNombre());

        JTextArea txtObs =
                new JTextArea();

        JPanel panel =
                new JPanel(new GridLayout(0, 1));

        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);

        panel.add(new JLabel("Observaciones:"));
        panel.add(new JScrollPane(txtObs));

        String nombreAnterior =
                area.getNombre();

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Editar Area",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        boolean cambioNombre =
                !nombreAnterior.equals(
                        txtNombre.getText()
                );

        if (!cambioNombre) {
                JOptionPane.showMessageDialog(
                        this,
                        "No se registro ningun cambio.",
                        "Sin cambios",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        AreaDAO dao = new AreaDAO();

        dao.editarArea(
                area.getId(),
                nombreAnterior,
                txtNombre.getText(),
                txtObs.getText(),
                cambioNombre
        );

        cargarAreas();
    }

    // =========================
    // ELIMINAR
    // =========================
    private void eliminar() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(this,
                    "Seleccione un área");

            return;
        }

        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Eliminar área?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        Area area = listaAreas.get(fila);

        AreaDAO dao = new AreaDAO();

        if(dao.tieneBienesAsignados(area.getId())) {

                JTextArea txtObs = new JTextArea(5,20);

                JPanel panel = new JPanel(new BorderLayout());

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

                dao.quitarAreaDeBienes(
                        area.getId(),
                        1,
                        txtObs.getText(),
                        area.getNombre()
                );
        }

        dao.eliminarArea(area.getId());

        cargarAreas();
    }
}