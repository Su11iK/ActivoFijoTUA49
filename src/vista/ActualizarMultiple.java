package vista;

import dao.BienDAO;
import modelo.Bien;
import ui.components.PrimaryButton;
import ui.components.SearchField;

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

    private JTextArea txtObservaciones;

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

        setSize(800, 550);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 3, 5, 5));

        // =========================
        // DESCRIPCION
        // =========================
        add(new JLabel("Descripción"));

        txtDescripcion = new SearchField();
        txtDescripcion.setColumns(25);
        add(txtDescripcion);

        add(new JLabel(""));
        
        // =========================
        // MARCA
        // =========================
        add(new JLabel("Marca"));

        txtMarca = new SearchField();
        txtMarca.setColumns(25);
        add(txtMarca);

        chkMarca = new JCheckBox("Sin marca");
        add(chkMarca);

        // =========================
        // MODELO
        // =========================
        add(new JLabel("Modelo"));

        txtModelo = new SearchField();
        txtModelo.setColumns(25);
        add(txtModelo);

        chkModelo = new JCheckBox("Sin modelo");
        add(chkModelo);

        // =========================
        // PROVEEDOR
        // =========================
        add(new JLabel("Proveedor"));

        txtProveedor = new SearchField();
        txtProveedor.setColumns(25);
        add(txtProveedor);

        chkProveedor = new JCheckBox("Sin proveedor");
        add(chkProveedor);

        // =========================
        // FACTURA
        // =========================
        add(new JLabel("Factura"));

        txtFactura = new SearchField();
        txtFactura.setColumns(25);
        add(txtFactura);

        chkFactura = new JCheckBox("Sin factura");
        add(chkFactura);

        // =========================
        // ESTADO
        // =========================
        add(new JLabel("Estado físico"));

        cbEstado = new JComboBox<>();

        cbEstado.addItem("");
        cbEstado.addItem("BUENO");
        cbEstado.addItem("REGULAR");
        cbEstado.addItem("MALO");
        cbEstado.addItem("USADO");

        add(cbEstado);

        add(new JLabel(""));

        // =========================
        // TIPO BIEN
        // =========================
        add(new JLabel("Tipo Bien"));

        cbTipoBien = new JComboBox<>();

        cbTipoBien.addItem("");
        cbTipoBien.addItem("MUEBLE");
        cbTipoBien.addItem("ELECTRONICO");

        add(cbTipoBien);

        add(new JLabel(""));
        
        add(new JLabel("Estatus"));

        cbStatus = new JComboBox<>(new String[] {
                "",
                "ACTIVO",
                "MANTENIMIENTO",
                "REPARACION"
        });

        add(cbStatus);

        add(new JLabel(""));

        // =========================
        // FECHA ACTUALIZACION
        // =========================
        add(new JLabel("Fecha actualización"));

        SearchField txtFecha = new SearchField(
                LocalDateTime.now().toString());
        txtFecha.setColumns(25);

        txtFecha.setEnabled(false);

        add(txtFecha);

        add(new JLabel(""));

        // =========================
        // OBSERVACIONES
        // =========================
        add(new JLabel("Observaciones"));

        txtObservaciones = new JTextArea();

        add(new JScrollPane(txtObservaciones));

        add(new JLabel(""));

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
        PrimaryButton btnActualizar =
                new PrimaryButton("Actualizar Todo");

        PrimaryButton btnCancelar =
                new PrimaryButton("Cancelar");

        add(btnActualizar);
        add(btnCancelar);

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

        if(!cambios){

            JOptionPane.showMessageDialog(
                    this,
                    "No se especificó ningún cambio."
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

        JOptionPane.showMessageDialog(this,
                "Bienes actualizados");

        dispose();
    }
}