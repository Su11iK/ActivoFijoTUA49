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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

        modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                        return false;
                }
        };

        modelo.addColumn("Inventario");

        modelo.addColumn("Usuario");

        modelo.addColumn("Fecha Baja");

        modelo.addColumn("Observaciones");

        tabla = new JTable(modelo);

        tabla.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {

                        if (e.getClickCount() == 2) {

                                int fila = tabla.getSelectedRow();
                                int columna = tabla.getSelectedColumn();
                                int columnaObservaciones =
                                        tabla.getColumnModel()
                                                .getColumnIndex("Observaciones");

                                // Cambia el índice por el de la columna Observaciones
                                if (columna == columnaObservaciones) {

                                String observaciones =
                                        tabla.getValueAt(fila, columna).toString();

                                mostrarObservaciones(observaciones);
                                }
                        }
                }
        });

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

                    b.getNombreEquipo(),

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

                        || (b.getNombreEquipo() != null
                                && b.getNombreEquipo()
                                        .toLowerCase()
                                        .contains(texto))

                        || (b.getMotivo() != null
                                && b.getMotivo()
                                        .toLowerCase()
                                        .contains(texto))) {

                modelo.addRow(new Object[] {

                        b.getNumeroInventario(),

                        b.getNombreEquipo(),

                        b.getFechaBaja(),

                        b.getMotivo()
                });
                }
        }
    }

    private void mostrarObservaciones(String texto) {

        JTextArea area = new JTextArea(texto);

        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setCaretPosition(0);

        JScrollPane scroll = new JScrollPane(area);

        scroll.setPreferredSize(new Dimension(500, 300));

        JOptionPane.showMessageDialog(
                this,
                scroll,
                "Observaciones",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}