package vista;

import dao.BienDAO;
import modelo.Bien;
import ui.components.PrimaryButton;
import ui.components.RoundedTextArea;
import ui.components.SearchField;
import ui.components.SecondaryButton;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class ActualizarBien extends JDialog {

    private Bien bien;

    private SearchField txtInventario;
    private SearchField txtDescripcion;
    private SearchField txtMarca;
    private SearchField txtModelo;
    private SearchField txtSerie;
    private SearchField txtProveedor;
    private SearchField txtFactura;

    private JCheckBox chkMarca;
    private JCheckBox chkModelo;
    private JCheckBox chkSerie;
    private JCheckBox chkProveedor;
    private JCheckBox chkFactura;

    private JComboBox<String> cbEstado;
    private JComboBox<String> cbTipoBien;
    private JComboBox<String> cbStatus;

    private RoundedTextArea txtObservaciones;

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

        txtInventario = new SearchField(bien.getNumeroInventario());
        txtInventario.setColumns(25);
        txtInventario.setEnabled(false);

        add(txtInventario);

        add(new JLabel(""));

        // =========================
        // DESCRIPCION
        // =========================
        add(new JLabel("Descripción"));

        txtDescripcion = new SearchField(bien.getDescripcion());
        txtDescripcion.setColumns(25);
        add(txtDescripcion);

        add(new JLabel(""));

        // =========================
        // MARCA
        // =========================
        add(new JLabel("Marca"));

        txtMarca = new SearchField(bien.getMarca());
        txtMarca.setColumns(25);
        add(txtMarca);

        chkMarca = new JCheckBox("Sin marca");
        add(chkMarca);

        // =========================
        // MODELO
        // =========================
        add(new JLabel("Modelo"));

        txtModelo = new SearchField(bien.getModelo());
        txtModelo.setColumns(25);
        add(txtModelo);

        chkModelo = new JCheckBox("Sin modelo");
        add(chkModelo);

        // =========================
        // SERIE
        // =========================
        add(new JLabel("Serie"));

        txtSerie = new SearchField(bien.getNumeroSerie());
        txtSerie.setColumns(25);
        add(txtSerie);

        chkSerie = new JCheckBox("Sin serie");
        add(chkSerie);

        // =========================
        // PROVEEDOR
        // =========================
        add(new JLabel("Proveedor"));

        txtProveedor = new SearchField(bien.getProveedor());
        txtProveedor.setColumns(25);
        add(txtProveedor);

        chkProveedor = new JCheckBox("Sin proveedor");
        add(chkProveedor);

        // =========================
        // FACTURA
        // =========================
        add(new JLabel("Factura"));

        txtFactura = new SearchField(bien.getFactura());
        txtFactura.setColumns(25);
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

        txtObservaciones = new RoundedTextArea(5, 20);

        JScrollPane scroll = new JScrollPane(txtObservaciones);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        add(scroll);

        add(new JLabel(""));

        // =========================
        // BOTONES
        // =========================
        PrimaryButton btnActualizar = new PrimaryButton("Actualizar");
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");

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

        boolean cambios =
        !bien.getDescripcion().equals(txtDescripcion.getText())
        || !bien.getMarca().equals(txtMarca.getText())
        || !bien.getModelo().equals(txtModelo.getText())
        || !bien.getNumeroSerie().equals(txtSerie.getText())
        || !bien.getProveedor().equals(txtProveedor.getText())
        || !bien.getFactura().equals(txtFactura.getText())
        || !bien.getEstadoFisico().equals(
                cbEstado.getSelectedItem().toString())
        || !bien.getTipoBien().equals(
                cbTipoBien.getSelectedItem().toString())
        || !bien.getStatus().equals(
                cbStatus.getSelectedItem().toString());

        if (!cambios) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se realizaron cambios."
            );

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
                    "",
                    bien.getResguardante(),
                    ""
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