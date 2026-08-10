package vista;

import dao.AreaDAO;
import modelo.Area;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;
import ui.components.RoundedTextArea;
import ui.components.SearchField;
import ui.components.SecondaryButton;
import ui.components.TableStyle;
import ui.theme.AppColors;
import ui.utils.UIUtils;

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

        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        ((JComponent) getContentPane()).setBorder(
                UIUtils.createPadding(25, 25, 20, 20)
        );
        getContentPane().setBackground(AppColors.PRIMARY_LIGHT);

        // =========================
        // TABLA
        // =========================
        modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                        return false;
                }
        };

        modelo.addColumn("Nombre Área");

        tabla = new JTable(modelo);
        TableStyle.apply(tabla);

        // =========================
        // BOTONES
        // =========================
        RoundedPanel panelBotones = new RoundedPanel();
        panelBotones.setLayout(
                new BoxLayout(panelBotones, BoxLayout.Y_AXIS)
        );
        panelBotones.setOpaque(false);
        panelBotones.setBackground(Color.WHITE);

        PrimaryButton btnAgregar =
                new PrimaryButton("Agregar");

        PrimaryButton btnEditar =
                new PrimaryButton("Editar");

        SecondaryButton btnEliminar =
                new SecondaryButton("Eliminar");

        UIUtils.setFixedSize(btnAgregar, 100, 40);
        UIUtils.setFixedSize(btnEditar, 100, 40);
        UIUtils.setFixedSize(btnEliminar, 100, 40);

        btnAgregar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnEditar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnEliminar.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panelBotones.add(btnAgregar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnEditar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnEliminar);

        RoundedPanel card = new RoundedPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BorderLayout(15, 0));
        card.setBorder(UIUtils.createPadding(25,25,25,25));

        card.add(new JScrollPane(tabla), BorderLayout.CENTER);
        card.add(panelBotones, BorderLayout.EAST);
        add(card);

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

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        mensaje.setText("Nombre del área:");
        String nombre = JOptionPane.showInputDialog(
                this,
                panel
        );

        if (nombre == null) {
                return;
        }

        if (nombre.trim().isEmpty()) {

            mensaje.setText("El nombre es obligatorios");
            JOptionPane.showMessageDialog(
                    this,
                    panel
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

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            mensaje.setText("Seleccione un área");
            JOptionPane.showMessageDialog(this,
                    panel);

            return;
        }

        Area area = listaAreas.get(fila);

        SearchField txtNombre =
                new SearchField(area.getNombre());
        txtNombre.setColumns(25);

        RoundedTextArea txtObs =
                new RoundedTextArea();
        JScrollPane scroll = new JScrollPane(txtObs);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        
        RoundedPanel panel1 = new RoundedPanel(
                new GridLayout(0, 1));
        panel1.setBackground(Color.WHITE);
        panel1.setBorder(UIUtils.createPadding(25,25,25,25));

        panel1.add(new JLabel("Nombre:"));
        panel1.add(txtNombre);

        panel1.add(new JLabel("Observaciones:"));
        panel1.add(scroll);

        String nombreAnterior =
                area.getNombre();

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel1,
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
                mensaje.setText("No se registro ningun cambio");
                JOptionPane.showMessageDialog(
                        this,
                        panel,
                        "Sin cambios",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        if (txtNombre.getText().trim().isEmpty()) {

            mensaje.setText("El nombre es obligatorios");
            JOptionPane.showMessageDialog(
                    this,
                    panel
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

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        int fila = tabla.getSelectedRow();

        if (fila == -1) {

            mensaje.setText("Seleccione un área");
            JOptionPane.showMessageDialog(this,
                    panel);

            return;
        }

        mensaje.setText("¿Eliminar área?");
        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        Area area = listaAreas.get(fila);

        AreaDAO dao = new AreaDAO();

        if(dao.tieneBienesAsignados(area.getId())) {

                RoundedTextArea txtObs = new RoundedTextArea(5,20);
                JScrollPane scroll = new JScrollPane(txtObs);
                scroll.setBorder(BorderFactory.createEmptyBorder());
                scroll.getViewport().setBackground(Color.WHITE);

                RoundedPanel panel1 = new RoundedPanel(
                        new GridLayout(0, 1));
                panel1.setBackground(Color.WHITE);
                panel1.setBorder(UIUtils.createPadding(25,25,25,25));

                panel1.add(
                new JLabel("Observaciones:"),
                BorderLayout.NORTH
                );

                panel1.add(
                scroll,
                BorderLayout.CENTER
                );

                int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel1,
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