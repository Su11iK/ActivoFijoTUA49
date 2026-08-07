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

        setSize(900, 750);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 1, 5, 5));
        ((JComponent) getContentPane()).setBorder(
                UIUtils.createPadding(25, 25, 20, 20)
        );
        getContentPane().setBackground(AppColors.PRIMARY_LIGHT);

        // =========================
        // INVENTARIO
        // =========================

        txtInventario = new SearchField(bien.getNumeroInventario());
        txtInventario.setColumns(25);
        txtInventario.setEnabled(false);

        // =========================
        // DESCRIPCION
        // =========================

        txtDescripcion = new SearchField(bien.getDescripcion());
        txtDescripcion.setColumns(25);

        // =========================
        // MARCA
        // =========================

        txtMarca = new SearchField(bien.getMarca());
        txtMarca.setColumns(25);

        chkMarca = new JCheckBox("Sin marca");

        // =========================
        // MODELO
        // =========================

        txtModelo = new SearchField(bien.getModelo());
        txtModelo.setColumns(25);

        chkModelo = new JCheckBox("Sin modelo");

        // =========================
        // SERIE
        // =========================

        txtSerie = new SearchField(bien.getNumeroSerie());
        txtSerie.setColumns(25);

        chkSerie = new JCheckBox("Sin serie");

        // =========================
        // PROVEEDOR
        // =========================

        txtProveedor = new SearchField(bien.getProveedor());
        txtProveedor.setColumns(25);

        chkProveedor = new JCheckBox("Sin proveedor");

        // =========================
        // FACTURA
        // =========================

        txtFactura = new SearchField(bien.getFactura());
        txtFactura.setColumns(25);

        chkFactura = new JCheckBox("Sin factura");

        // =========================
        // ESTADO
        // =========================

        cbEstado = new JComboBox<>();

        cbEstado.addItem("BUENO");
        cbEstado.addItem("REGULAR");
        cbEstado.addItem("MALO");
        cbEstado.addItem("USADO");

        cbEstado.setSelectedItem(bien.getEstadoFisico());

        // =========================
        // TIPO BIEN
        // =========================

        cbTipoBien = new JComboBox<>();

        cbTipoBien.addItem("MUEBLE");
        cbTipoBien.addItem("ELECTRONICO");

        // 🔥 seleccionar el actual
        cbTipoBien.setSelectedItem(bien.getTipoBien());

        cbStatus = new JComboBox<>(new String[] {
                "ACTIVO",
                "MANTENIMIENTO",
                "REPARACION"
        });

        cbStatus.setSelectedItem(bien.getStatus());

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
        // BOTONES
        // =========================
        PrimaryButton btnActualizar = new PrimaryButton("Actualizar");
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");

        RoundedPanel card = new RoundedPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(0, 3, 5, 5));
        card.setBorder(UIUtils.createPadding(25,25,25,25));

        card.add(new JLabel("No. Inventario"));
        card.add(txtInventario);
        card.add(new JLabel(""));
        card.add(new JLabel("Descripción"));
        card.add(txtDescripcion);
        card.add(new JLabel(""));
        card.add(new JLabel("Marca"));
        card.add(txtMarca);
        card.add(chkMarca);
        card.add(new JLabel("Modelo"));
        card.add(txtModelo);
        card.add(chkModelo);
        card.add(new JLabel("Serie"));
        card.add(txtSerie);
        card.add(chkSerie);
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