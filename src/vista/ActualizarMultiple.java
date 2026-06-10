package vista;

import dao.BienDAO;
import modelo.Bien;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class ActualizarMultiple extends JDialog {

    private JTextField txtDescripcion;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtProveedor;
    private JTextField txtFactura;

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

        txtDescripcion = new JTextField();
        add(txtDescripcion);

        add(new JLabel(""));
        
        // =========================
        // MARCA
        // =========================
        add(new JLabel("Marca"));

        txtMarca = new JTextField();
        add(txtMarca);

        chkMarca = new JCheckBox("Sin marca");
        add(chkMarca);

        // =========================
        // MODELO
        // =========================
        add(new JLabel("Modelo"));

        txtModelo = new JTextField();
        add(txtModelo);

        chkModelo = new JCheckBox("Sin modelo");
        add(chkModelo);

        // =========================
        // PROVEEDOR
        // =========================
        add(new JLabel("Proveedor"));

        txtProveedor = new JTextField();
        add(txtProveedor);

        chkProveedor = new JCheckBox("Sin proveedor");
        add(chkProveedor);

        // =========================
        // FACTURA
        // =========================
        add(new JLabel("Factura"));

        txtFactura = new JTextField();
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

        JTextField txtFecha = new JTextField(
                LocalDateTime.now().toString());

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
        JButton btnActualizar =
                new JButton("Actualizar Todo");

        JButton btnCancelar =
                new JButton("Cancelar");

        add(btnActualizar);
        add(btnCancelar);

        btnActualizar.addActionListener(e -> actualizarTodo());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void configurarCheck(JCheckBox check, JTextField campo, String valor) {

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

        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta llenar");
            return;
        }

        if (txtMarca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta llenar");
            return;
        }

        if (txtModelo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta llenar");
            return;
        }

        if (txtProveedor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta llenar");
            return;
        }

        if (txtFactura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta llenar");
            return;
        }

        if (cbEstado.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta llenar");
            return;
        }

        if (cbTipoBien.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Falta llenar");
            return;
        }

        for (int fila : filas) {

            Bien b = listaBienes.get(fila);
            String status = b.getStatus();

            b.setDescripcion(txtDescripcion.getText());
            b.setMarca(txtMarca.getText());
            b.setModelo(txtModelo.getText());
            b.setProveedor(txtProveedor.getText());
            b.setFactura(txtFactura.getText());

            b.setEstadoFisico(
                    cbEstado.getSelectedItem().toString());

            b.setTipoBien(
                    cbTipoBien.getSelectedItem().toString());

            b.setStatus(
            cbStatus.getSelectedItem().toString());
            
            dao.actualizarMultiple(b);

            dao.registrarMovimiento(
                    b.getId(),
                    1,
                    txtObservaciones.getText(),
                    "ACTUALIZACION",
                    status
            );
        }

        JOptionPane.showMessageDialog(this,
                "Bienes actualizados");

        dispose();
    }
}