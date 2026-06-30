package vista;

import dao.BajaDAO;
import modelo.Baja;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.event.DocumentEvent;

public class BajasFrame extends JFrame {

    private JTable tabla;

    private DefaultTableModel modelo;

    private JTextField txtBuscar;

    private List<Baja> listaBajas;

    public BajasFrame() {

        setTitle("Bajas");

        setExtendedState(
                JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // =========================
        // PANEL SUPERIOR
        // =========================

        JButton btnMovimientos = new JButton("Movimientos");
        JButton btnBienes = new JButton("Bienes");

        JPanel panelBotonesSuperior = new JPanel();

        panelBotonesSuperior.add(btnMovimientos);
        panelBotonesSuperior.add(btnBienes);

        JPanel panelSuperior = new JPanel(new BorderLayout());

        panelSuperior.add(
                new JLabel("Buscar: "),
                BorderLayout.WEST
        );

        txtBuscar = new JTextField();

        panelSuperior.add(
                txtBuscar,
                BorderLayout.CENTER
        );

        add(panelSuperior, BorderLayout.NORTH);

        JPanel contenedorSuperior = new JPanel(new BorderLayout());

        contenedorSuperior.add(panelBotonesSuperior, BorderLayout.NORTH);
        contenedorSuperior.add(panelSuperior, BorderLayout.SOUTH);

        add(contenedorSuperior, BorderLayout.NORTH);

        txtBuscar.getDocument().addDocumentListener(
            new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {
                    filtrarTabla();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    filtrarTabla();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    filtrarTabla();
                }
            }
        );

        // =========================
        // TABLA
        // =========================

        modelo =
                new DefaultTableModel();

        modelo.addColumn("Inventario");

        modelo.addColumn("Usuario");

        modelo.addColumn("Fecha Baja");

        modelo.addColumn("Observaciones");

        tabla =
                new JTable(modelo);

        tabla.setAutoCreateRowSorter(true);

        add(new JScrollPane(tabla),
                BorderLayout.CENTER);

        // =========================
        // EVENTOS
        // =========================

        btnBienes.addActionListener(e -> {

            Principal p =
                    new Principal();

            p.setVisible(true);

            dispose();
        });

        btnMovimientos.addActionListener(e -> {

            MovimientosFrame m =
                    new MovimientosFrame();

            m.setVisible(true);

            dispose();
        });

        cargarBajas();
    }

    private void cargarBajas() {

        BajaDAO dao =
                new BajaDAO();

        listaBajas =
                dao.listarBajas();

        modelo.setRowCount(0);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        for(Baja b : listaBajas) {

            modelo.addRow(new Object[]{

                    b.getNumeroInventario(),

                    b.getUsuario(),

                    formato.format(b.getFechaBaja()),

                    b.getMotivo()
            });
        }
    }

    private void filtrarTabla() {

        String texto =
                txtBuscar.getText()
                        .toLowerCase();

        modelo.setRowCount(0);

        for (Baja b : listaBajas) {

                if ((b.getNumeroInventario() != null
                        && b.getNumeroInventario()
                                .toLowerCase()
                                .contains(texto))

                        || (b.getUsuario() != null
                                && b.getUsuario()
                                        .toLowerCase()
                                        .contains(texto))

                        || (b.getMotivo() != null
                                && b.getMotivo()
                                        .toLowerCase()
                                        .contains(texto))) {

                modelo.addRow(new Object[] {

                        b.getNumeroInventario(),

                        b.getUsuario(),

                        b.getFechaBaja(),

                        b.getMotivo()
                });
                }
        }
    }
}