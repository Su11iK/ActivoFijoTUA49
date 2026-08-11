package vista;

import dao.AreaDAO;
import dao.ResguardanteDAO;
import modelo.Area;
import modelo.Resguardante;
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

public class ResguardantesFrame extends JDialog {

    private JTable tabla;
    private DefaultTableModel modelo;

    private List<Resguardante> listaResguardantes;

    public ResguardantesFrame(JFrame parent) {

        super(parent,
                "Resguardantes",
                true);

        setSize(800, 600);
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

        modelo.addColumn("Nombre");
        modelo.addColumn("Puesto");
        modelo.addColumn("Área");

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

        PrimaryButton btnAsignar =
                new PrimaryButton("Asignar");

        SecondaryButton btnEliminar =
                new SecondaryButton("Eliminar");

        UIUtils.setFixedSize(btnAgregar, 100, 40);
        UIUtils.setFixedSize(btnEditar, 100, 40);
        UIUtils.setFixedSize(btnAsignar, 100, 40);
        UIUtils.setFixedSize(btnEliminar, 100, 40);

        btnAgregar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnEditar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnAsignar.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnEliminar.setAlignmentX(Component.RIGHT_ALIGNMENT);

        panelBotones.add(btnAgregar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnEditar);
        panelBotones.add(Box.createVerticalStrut(10));

        panelBotones.add(btnAsignar);
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

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

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

        RoundedPanel panel1 = new RoundedPanel(
                new GridLayout(0, 1));
        panel1.setBackground(Color.WHITE);
        panel1.setBorder(UIUtils.createPadding(25,25,25,25));

        panel1.add(new JLabel("Nombre"));
        panel1.add(txtNombre);

        panel1.add(new JLabel("Puesto"));
        panel1.add(txtPuesto);

        panel1.add(new JLabel("Área"));
        panel1.add(cbAreas);

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel1,
                        "Agregar Resguardante",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        if (txtNombre.getText().trim().isEmpty()
                || txtPuesto.getText().trim().isEmpty()) {

            mensaje.setText("Nombre y puesto son obligatorios");
            JOptionPane.showMessageDialog(
                    this,
                    panel
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

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            mensaje.setText("Seleccione un resguardante");
            JOptionPane.showMessageDialog(
                    this,
                    panel
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

        RoundedTextArea txtObs =
                new RoundedTextArea();
        JScrollPane scroll = new JScrollPane(txtObs);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        RoundedPanel panel1 =
                new RoundedPanel(new GridLayout(0, 1));
        panel1.setBackground(Color.WHITE);
        panel1.setBorder(UIUtils.createPadding(25,25,25,25));

        panel1.add(new JLabel("Nombre:"));
        panel1.add(txtNombre);

        panel1.add(new JLabel("Puesto:"));
        panel1.add(txtPuesto);

        panel1.add(new JLabel("Observaciones:"));
        panel1.add(scroll);

        String nombreAnterior =
                r.getNombre();

        String puestoAnterior =
                r.getPuesto();

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel1,
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
                mensaje.setText("No se registro ningun cambio.");
                JOptionPane.showMessageDialog(
                        this,
                        panel
                );

                return;
        }

        if (txtNombre.getText().trim().isEmpty()
                || txtPuesto.getText().trim().isEmpty()) {

            mensaje.setText("Nombre y puesto son obligatorios");
            JOptionPane.showMessageDialog(
                    this,
                    panel
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

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            mensaje.setText("Seleccione un resguardante");
            JOptionPane.showMessageDialog(
                    this,
                    panel
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

        RoundedTextArea txtObs =
                new RoundedTextArea();
        JScrollPane scroll = new JScrollPane(txtObs);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

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

        RoundedPanel panel1 =
                new RoundedPanel(new GridLayout(0, 1));
        panel1.setBackground(Color.WHITE);
        panel1.setBorder(UIUtils.createPadding(25,25,25,25));

        panel1.add(new JLabel("Área:"));
        panel1.add(cbAreas);

        panel1.add(new JLabel("Observaciones:"));
        panel1.add(scroll);

        int opcion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel1,
                        "Asignar Resguardante",
                        JOptionPane.OK_CANCEL_OPTION
                );

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        Area areaSeleccionada =
                (Area) cbAreas.getSelectedItem();

        if (areaSeleccionada == null) {
                mensaje.setText("No se registro ninguna asignación.");
                JOptionPane.showMessageDialog(
                        this,
                        panel
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
                mensaje.setText("No se registro ningun cambio.");
                JOptionPane.showMessageDialog(
                        this,
                        panel
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

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        int fila =
                tabla.getSelectedRow();

        if (fila == -1) {

            mensaje.setText("Seleccione un resguardante");
            JOptionPane.showMessageDialog(
                    this,
                    panel
            );

            return;
        }

        mensaje.setText("¿Eliminar resguardante?");
        int confirmar =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
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

                RoundedTextArea txtObs = new RoundedTextArea(5,20);
                JScrollPane scroll = new JScrollPane(txtObs);
                scroll.setBorder(BorderFactory.createEmptyBorder());
                scroll.getViewport().setBackground(Color.WHITE);

                RoundedPanel panel1 = new RoundedPanel(
                        new BorderLayout(10, 10));
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