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

public class AltaBien extends JDialog {

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

    private JRadioButton rbArrendamiento;
    private JRadioButton rbSinNumero;

    private JComboBox<String> cbEstado;
    private JComboBox<String> cbTipoBien;

    private RoundedTextArea txtObservaciones;

    public AltaBien(JFrame parent) {

        super(parent, "Alta de Bien", true);

        setSize(900, 600);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 1, 5, 5));
        ((JComponent) getContentPane()).setBorder(
                UIUtils.createPadding(25, 25, 20, 20)
        );
        getContentPane().setBackground(AppColors.PRIMARY_LIGHT);

        // =========================
        // INVENTARIO
        // =========================

        txtInventario = new SearchField();
        txtInventario.setColumns(25);

        RoundedPanel panelRadio = new RoundedPanel();
        panelRadio.setBackground(Color.WHITE);

        rbArrendamiento = new JRadioButton("ARRENDAMIENTO");
        rbSinNumero = new JRadioButton("SIN NUMERO");

        panelRadio.add(rbArrendamiento);
        panelRadio.add(rbSinNumero);

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
        // SERIE
        // =========================

        txtSerie = new SearchField();
        txtSerie.setColumns(25);

        chkSerie = new JCheckBox("Sin serie");

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
        cbEstado.addItem("BUENO");
        cbEstado.addItem("REGULAR");
        cbEstado.addItem("MALO");
        cbEstado.addItem("USADO");

        // =========================
        // TIPO BIEN
        // =========================

        cbTipoBien = new JComboBox<>();
        cbTipoBien.addItem("MUEBLE");
        cbTipoBien.addItem("ELECTRONICO");

        // =========================
        // FECHA
        // =========================

        SearchField txtFecha = new SearchField(LocalDateTime.now().toString());
        txtFecha.setColumns(25);
        txtFecha.setEnabled(false);

        // =========================
        // OBSERVACIONES
        // =========================

        txtObservaciones = new RoundedTextArea(1, 20);

        JScrollPane scroll = new JScrollPane(txtObservaciones);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // =========================
        // BOTONES
        // =========================
        PrimaryButton btnGuardar = new PrimaryButton("Subir Alta");
        btnGuardar.setButtonColor(AppColors.PRIMARY);
        btnGuardar.setHoverColor(AppColors.PRIMARY_DARK);
        btnGuardar.setMouseExited(AppColors.PRIMARY);
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");

        RoundedPanel card = new RoundedPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(0, 3, 5, 5));
        card.setBorder(UIUtils.createPadding(25,25,25,25));

        card.add(new JLabel("No. Inventario"));
        card.add(txtInventario);
        card.add(panelRadio);
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
        card.add(new JLabel("Fecha Alta"));
        card.add(txtFecha);
        card.add(new JLabel("ACTIVO"));
        card.add(new JLabel("Observaciones"));
        card.add(scroll);
        card.add(new JLabel(""));
        card.add(btnGuardar);
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
        // RADIOBUTTONS
        // =========================
        rbArrendamiento.addActionListener(e -> {

            if (rbArrendamiento.isSelected()) {
                rbSinNumero.setSelected(false);
                txtInventario.setText("ARRENDAMIENTO");
                txtInventario.setEnabled(false);
            } else {
                txtInventario.setEnabled(true);
                txtInventario.setText("");
            }
        });

        rbSinNumero.addActionListener(e -> {

            if (rbSinNumero.isSelected()) {
                rbArrendamiento.setSelected(false);
                txtInventario.setText("S/N");
                txtInventario.setEnabled(false);
            } else {
                txtInventario.setEnabled(true);
                txtInventario.setText("");
            }
        });

        // =========================
        // GUARDAR
        // =========================
        btnGuardar.addActionListener(e -> guardarBien());

        // =========================
        // CANCELAR
        // =========================
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

    private void guardarBien() {

        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción es obligatoria");
            return;
        }

        Bien b = new Bien();
        BienDAO dao = new BienDAO();

        // =========================
        // TIPO ADQUISICION
        // =========================
        if (rbArrendamiento.isSelected()) {
            b.setTipoAdquisicion("ARRENDAMIENTO");
            b.setNumeroInventario(dao.generarConsecutivoEspecial("ARRENDAMIENTO"));

        } else if (rbSinNumero.isSelected()) {
            b.setTipoAdquisicion("COMPRA");
            b.setNumeroInventario(dao.generarConsecutivoEspecial("S/N"));

        } else {

            if (txtInventario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Ingrese número de inventario");
                return;
            }

            b.setTipoAdquisicion("COMPRA");
            b.setNumeroInventario(txtInventario.getText());
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

        b.setDescripcion(txtDescripcion.getText());
        b.setMarca(txtMarca.getText());
        b.setModelo(txtModelo.getText());
        b.setNumeroSerie(txtSerie.getText());
        b.setProveedor(txtProveedor.getText());
        b.setFactura(txtFactura.getText());
        b.setEstadoFisico(cbEstado.getSelectedItem().toString());
        b.setTipoBien(cbTipoBien.getSelectedItem().toString());

        if(!rbArrendamiento.isSelected()
                && !rbSinNumero.isSelected()) {

            if(dao.existeNumeroInventario(
                    txtInventario.getText().trim())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe un bien con ese número de inventario.",
                        "Inventario duplicado",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }
        
        int idGenerado = dao.insertarBien(b);

        if (idGenerado != -1) {

            // 🔥 registrar movimiento ALTA
            dao.registrarMovimiento(
                    idGenerado,
                    1, // temporal usuario logueado
                    txtObservaciones.getText(),
                    "ALTA",
                    "",
                    "ACTIVO",
                    "",
                    "",
                    "",
                    ""

            );

            JOptionPane.showMessageDialog(this,
                    "Bien registrado correctamente");

            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Error al registrar");
        }
    }
}