package vista;

import dao.BienDAO;
import modelo.Bien;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;
import ui.components.RoundedTextArea;
import ui.components.SearchField;
import ui.components.SecondaryButton;
import ui.theme.AppColors;
import ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class ActualizarMultiple extends JDialog {

    private SearchField txtDescripcion;
    private SearchField txtMarca;
    private SearchField txtModelo;
    private SearchField txtProveedor;
    private SearchField txtFactura;

    private JCheckBox chkMarca;
    private JCheckBox chkModelo;
    private JCheckBox chkProveedor;
    private JCheckBox chkFactura;

    private JComboBox<String> cbEstado;
    private JComboBox<String> cbTipoBien;
    private JComboBox<String> cbStatus;

    private RoundedTextArea txtObservaciones;

    private int[] filas;
    private List<Bien> listaBienes;

    public ActualizarMultiple(
            JFrame parent,
            int[] filas,
            List<Bien> listaBienes
    ) {

        super(parent, "Actualización múltiple", true);

        this.filas = filas;
        this.listaBienes = listaBienes;

        setSize(900, 650);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 1, 5, 5));
        ((JComponent) getContentPane()).setBorder(
                UIUtils.createPadding(25, 25, 20, 20)
        );
        getContentPane().setBackground(AppColors.PRIMARY_LIGHT);

        // =========================
        // DESCRIPCION
        // =========================

        txtDescripcion = new SearchField();
        txtDescripcion.setColumns(25);
        
        // =========================
        // MARCA
        // =========================

        txtMarca = new SearchField();
        txtMarca.setColumns(25);

        chkMarca = new JCheckBox("Sin marca");

        // =========================
        // MODELO
        // =========================

        txtModelo = new SearchField();
        txtModelo.setColumns(25);

        chkModelo = new JCheckBox("Sin modelo");

        // =========================
        // PROVEEDOR
        // =========================

        txtProveedor = new SearchField();
        txtProveedor.setColumns(25);

        chkProveedor = new JCheckBox("Sin proveedor");

        // =========================
        // FACTURA
        // =========================

        txtFactura = new SearchField();
        txtFactura.setColumns(25);

        chkFactura = new JCheckBox("Sin factura");

        // =========================
        // ESTADO
        // =========================

        cbEstado = new JComboBox<>();

        cbEstado.addItem("");
        cbEstado.addItem("BUENO");
        cbEstado.addItem("REGULAR");
        cbEstado.addItem("MALO");
        cbEstado.addItem("USADO");

        // =========================
        // TIPO BIEN
        // =========================

        cbTipoBien = new JComboBox<>();

        cbTipoBien.addItem("");
        cbTipoBien.addItem("MUEBLE");
        cbTipoBien.addItem("ELECTRONICO");

        cbStatus = new JComboBox<>(new String[] {
                "",
                "ACTIVO",
                "MANTENIMIENTO",
                "REPARACION"
        });

        // =========================
        // FECHA ACTUALIZACION
        // =========================

        SearchField txtFecha = new SearchField(
                LocalDateTime.now().toString());
        txtFecha.setColumns(25);

        txtFecha.setEnabled(false);

        // =========================
        // OBSERVACIONES
        // =========================

        txtObservaciones = new RoundedTextArea();

        JScrollPane scroll = new JScrollPane(txtObservaciones);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // =========================
        // EVENTOS CHECKBOX
        // =========================
        configurarCheck(chkMarca, txtMarca, "S/M");
        configurarCheck(chkModelo, txtModelo, "S/MO");
        configurarCheck(chkProveedor, txtProveedor, "S/P");
        configurarCheck(chkFactura, txtFactura, "S/F");

        // =========================
        // BOTONES
        // =========================
        PrimaryButton btnActualizar = new PrimaryButton("Actualizar Todo");
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");

        RoundedPanel card = new RoundedPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(0, 3, 5, 5));
        card.setBorder(UIUtils.createPadding(25,25,25,25));

        card.add(new JLabel("Descripción"));
        card.add(txtDescripcion);
        card.add(new JLabel(""));
        card.add(new JLabel("Marca"));
        card.add(txtMarca);
        card.add(chkMarca);
        card.add(new JLabel("Modelo"));
        card.add(txtModelo);
        card.add(chkModelo);
        card.add(new JLabel("Proveedor"));
        card.add(txtProveedor);
        card.add(chkProveedor);
        card.add(new JLabel("Factura"));
        card.add(txtFactura);
        card.add(chkFactura);
        card.add(new JLabel("Estado físico"));
        card.add(cbEstado);
        card.add(new JLabel(""));
        card.add(new JLabel("Tipo Bien"));
        card.add(cbTipoBien);
        card.add(new JLabel(""));
        card.add(new JLabel("Estatus"));
        card.add(cbStatus);
        card.add(new JLabel(""));
        card.add(new JLabel("Fecha actualización"));
        card.add(txtFecha);
        card.add(new JLabel(""));
        card.add(new JLabel("Observaciones"));
        card.add(scroll);
        card.add(new JLabel(""));
        card.add(btnActualizar);
        card.add(btnCancelar);
        add(card);

        btnActualizar.addActionListener(e -> actualizarTodo());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void configurarCheck(JCheckBox check, SearchField campo, String valor) {

        check.addActionListener(e -> {

            if (check.isSelected()) {
                campo.setText(valor);
                campo.setEnabled(false);
            } else {
                campo.setText("");
                campo.setEnabled(true);
            }
        });
    }

    private void actualizarTodo() {

        BienDAO dao = new BienDAO();
        String statusAnterior = "";

        boolean cambios =
        !txtDescripcion.getText().trim().isEmpty()
        || !txtMarca.getText().trim().isEmpty()
        || !txtModelo.getText().trim().isEmpty()
        || !txtProveedor.getText().trim().isEmpty()
        || !txtFactura.getText().trim().isEmpty()
        || !cbEstado.getSelectedItem().toString().isEmpty()
        || !cbTipoBien.getSelectedItem().toString().isEmpty()
        || !cbStatus.getSelectedItem().toString().isEmpty();

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        if(!cambios){

            mensaje.setText("No se especificó ningún cambio");
            JOptionPane.showMessageDialog(
                    this,
                    panel
            );

            return;
        }

        for (int fila : filas) {

            Bien b = listaBienes.get(fila);

            if(!txtDescripcion.getText().trim().isEmpty()){
                b.setDescripcion(txtDescripcion.getText().trim());}

            if(!txtMarca.getText().trim().isEmpty()){
                b.setMarca(txtMarca.getText().trim());}

            if(!txtModelo.getText().trim().isEmpty()){
                b.setModelo(txtModelo.getText().trim());}

            if(!txtProveedor.getText().trim().isEmpty()){
                b.setProveedor(txtProveedor.getText().trim());}

            if(!txtFactura.getText().trim().isEmpty()){
                b.setFactura(txtFactura.getText().trim());}

            if(!cbEstado.getSelectedItem().toString().isEmpty()){
                b.setEstadoFisico(cbEstado.getSelectedItem().toString());}

            if(!cbTipoBien.getSelectedItem().toString().isEmpty()){
                b.setTipoBien(cbTipoBien.getSelectedItem().toString());}

            if(!cbStatus.getSelectedItem().toString().isEmpty()){
                statusAnterior = b.getStatus();
                b.setStatus(cbStatus.getSelectedItem().toString());}
            
            dao.actualizarMultiple(b);

            dao.registrarMovimiento(
                    b.getId(),
                    1,
                    txtObservaciones.getText(),
                    "ACTUALIZACION",
                    statusAnterior,
                    b.getStatus(),
                    b.getArea(),
                    "",
                    b.getResguardante(),
                    ""
            );
        }

        mensaje.setText("Bienes actualizados");
        JOptionPane.showMessageDialog(this,
                panel);

        dispose();
    }
}