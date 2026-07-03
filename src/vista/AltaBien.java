package vista;

import dao.BienDAO;
import modelo.Bien;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AltaBien extends JDialog {

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

    private JRadioButton rbArrendamiento;
    private JRadioButton rbSinNumero;

    private JComboBox<String> cbEstado;
    private JComboBox<String> cbTipoBien;

    private JTextArea txtObservaciones;

    public AltaBien(JFrame parent) {

        super(parent, "Alta de Bien", true);

        setSize(800, 550);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 3, 5, 5));

        // =========================
        // INVENTARIO
        // =========================
        add(new JLabel("No. Inventario"));

        txtInventario = new JTextField();
        add(txtInventario);

        txtInventario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {

                char c = e.getKeyChar();

                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        JPanel panelRadio = new JPanel();

        rbArrendamiento = new JRadioButton("ARRENDAMIENTO");
        rbSinNumero = new JRadioButton("SIN NUMERO");

        panelRadio.add(rbArrendamiento);
        panelRadio.add(rbSinNumero);

        add(panelRadio);

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
        // SERIE
        // =========================
        add(new JLabel("Serie"));

        txtSerie = new JTextField();
        add(txtSerie);

        chkSerie = new JCheckBox("Sin serie");
        add(chkSerie);

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
        cbTipoBien.addItem("MUEBLE");
        cbTipoBien.addItem("ELECTRONICO");

        add(cbTipoBien);

        add(new JLabel(""));

        // =========================
        // FECHA
        // =========================
        add(new JLabel("Fecha Alta"));

        JTextField txtFecha = new JTextField(LocalDateTime.now().toString());
        txtFecha.setEnabled(false);

        add(txtFecha);

        add(new JLabel("ACTIVO"));

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
        JButton btnGuardar = new JButton("Subir Alta");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnGuardar);
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