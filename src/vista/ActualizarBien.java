package vista;

import dao.BienDAO;
import modelo.Bien;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class ActualizarBien extends JDialog {

    private Bien bien;

    private JTextField txtInventario;
    private JTextField txtDescripcion;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtSerie;
    private JTextField txtProveedor;
    private JTextField txtFactura;

    private JCheckBox chkMarca;
    private JCheckBox chkModelo;
    private JCheckBox chkSerie;
    private JCheckBox chkProveedor;
    private JCheckBox chkFactura;

    private JComboBox<String> cbEstado;
    private JComboBox<String> cbTipoBien;
    private JComboBox<String> cbStatus;

    private JTextArea txtObservaciones;

    public ActualizarBien(JFrame parent, Bien bienSeleccionado) {

        super(parent, "Actualizar Bien", true);

        this.bien = bienSeleccionado;

        setSize(800, 550);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 3, 5, 5));

        // =========================
        // INVENTARIO
        // =========================
        add(new JLabel("No. Inventario"));

        txtInventario = new JTextField(bien.getNumeroInventario());
        txtInventario.setEnabled(false);

        add(txtInventario);

        add(new JLabel(""));

        // =========================
        // DESCRIPCION
        // =========================
        add(new JLabel("Descripción"));

        txtDescripcion = new JTextField(bien.getDescripcion());
        add(txtDescripcion);

        add(new JLabel(""));

        // =========================
        // MARCA
        // =========================
        add(new JLabel("Marca"));

        txtMarca = new JTextField(bien.getMarca());
        add(txtMarca);

        chkMarca = new JCheckBox("Sin marca");
        add(chkMarca);

        // =========================
        // MODELO
        // =========================
        add(new JLabel("Modelo"));

        txtModelo = new JTextField(bien.getModelo());
        add(txtModelo);

        chkModelo = new JCheckBox("Sin modelo");
        add(chkModelo);

        // =========================
        // SERIE
        // =========================
        add(new JLabel("Serie"));

        txtSerie = new JTextField(bien.getNumeroSerie());
        add(txtSerie);

        chkSerie = new JCheckBox("Sin serie");
        add(chkSerie);

        // =========================
        // PROVEEDOR
        // =========================
        add(new JLabel("Proveedor"));

        txtProveedor = new JTextField(bien.getProveedor());
        add(txtProveedor);

        chkProveedor = new JCheckBox("Sin proveedor");
        add(chkProveedor);

        // =========================
        // FACTURA
        // =========================
        add(new JLabel("Factura"));

        txtFactura = new JTextField(bien.getFactura());
        add(txtFactura);

        chkFactura = new JCheckBox("Sin factura");
        add(chkFactura);

        // =========================
        // ESTADO
        // =========================
        add(new JLabel("Estado físico"));

        cbEstado = new JComboBox<>();

        cbEstado.addItem("BUENO");
        cbEstado.addItem("REGULAR");
        cbEstado.addItem("MALO");
        cbEstado.addItem("USADO");

        cbEstado.setSelectedItem(bien.getEstadoFisico());

        add(cbEstado);

        add(new JLabel(""));

        // =========================
        // TIPO BIEN
        // =========================
        add(new JLabel("Tipo Bien"));

        cbTipoBien = new JComboBox<>();

        cbTipoBien.addItem("MUEBLE");
        cbTipoBien.addItem("ELECTRONICO");

        // 🔥 seleccionar el actual
        cbTipoBien.setSelectedItem(bien.getTipoBien());

        add(cbTipoBien);

        add(new JLabel(""));

        add(new JLabel("Estatus"));

        cbStatus = new JComboBox<>(new String[] {
                "ACTIVO",
                "MANTENIMIENTO",
                "REPARACION"
        });

        cbStatus.setSelectedItem(bien.getStatus());

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

        txtObservaciones = new JTextArea(5, 20);

        JScrollPane scroll = new JScrollPane(txtObservaciones);

        add(scroll);

        add(new JLabel(""));

        // =========================
        // BOTONES
        // =========================
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnActualizar);
        add(btnCancelar);

        // =========================
        // EVENTOS CHECKBOX
        // =========================
        configurarCheck(chkMarca, txtMarca, "S/M");
        configurarCheck(chkModelo, txtModelo, "S/MO");
        configurarCheck(chkSerie, txtSerie, "S/S");
        configurarCheck(chkProveedor, txtProveedor, "S/P");
        configurarCheck(chkFactura, txtFactura, "S/F");

        // =========================
        // EVENTOS
        // =========================
        btnActualizar.addActionListener(e -> actualizar());

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

    private void actualizar() {

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

        if (txtSerie.getText().trim().isEmpty()) {
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

        bien.setDescripcion(txtDescripcion.getText());
        bien.setMarca(txtMarca.getText());
        bien.setModelo(txtModelo.getText());
        bien.setNumeroSerie(txtSerie.getText());
        bien.setProveedor(txtProveedor.getText());
        bien.setFactura(txtFactura.getText());

        bien.setEstadoFisico(
            cbEstado.getSelectedItem().toString());

        bien.setTipoBien(
            cbTipoBien.getSelectedItem().toString());

        String statusAnterior = bien.getStatus();

        bien.setStatus(
            cbStatus.getSelectedItem().toString());

        boolean actualizado = dao.actualizarBien(bien);

        if (actualizado) {

            // 🔥 registrar movimiento
            dao.registrarMovimiento(
                    bien.getId(),
                    1,
                    txtObservaciones.getText(),
                    "ACTUALIZACION",
                    statusAnterior,
                    bien.getStatus(),
                    bien.getArea(),
                    bien.getResguardante()
            );

            JOptionPane.showMessageDialog(this,
                    "Bien actualizado correctamente");

            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Error al actualizar");
        }
    }
}