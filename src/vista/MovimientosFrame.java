package vista;

import dao.MovimientoDAO;
import modelo.Movimiento;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.event.DocumentEvent;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MovimientosFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtBuscar;
    private List<Movimiento> listaMovimientos;

    public MovimientosFrame() {

        setTitle("Movimientos");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                        return false;
                }
        };

        modelo.addColumn("Fecha");
        modelo.addColumn("Inventario");
        modelo.addColumn("Usuario");

        modelo.addColumn("Área Anterior");
        modelo.addColumn("Área Nueva");

        modelo.addColumn("Resguardante Anterior");
        modelo.addColumn("Resguardante Nuevo");

        modelo.addColumn("Tipo");
        modelo.addColumn("Status Anterior");
        modelo.addColumn("Status Nuevo");
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

        JButton btnBienes = new JButton("Bienes");
        JButton btnBajas = new JButton("Bajas");

        JPanel panelBotonesSuperior = new JPanel();

        panelBotonesSuperior.add(btnBienes);
        panelBotonesSuperior.add(btnBajas);

        btnBienes.addActionListener(e -> {

            Principal p =
                    new Principal();

            p.setVisible(true);

            dispose();
        });

        btnBajas.addActionListener(e -> {

            BajasFrame bajas =
                    new BajasFrame();

            bajas.setVisible(true);

            dispose();
        });

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

        add(
            new JScrollPane(tabla),
            BorderLayout.CENTER
        );

        cargarMovimientos();
    }

    private void cargarMovimientos() {

        MovimientoDAO dao =
                new MovimientoDAO();

        listaMovimientos =
                dao.listarMovimientos();

        modelo.setRowCount(0);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        for(Movimiento m :
                listaMovimientos) {

            modelo.addRow(new Object[]{

                formato.format(m.getFechaMovimiento()),

                m.getNumeroInventario(),
                m.getNombreEquipo(),

                m.getAreaAnterior(),
                m.getAreaNueva(),

                m.getResguardanteAnterior(),
                m.getResguardanteNuevo(),

                m.getTipoMovimiento(),
                m.getStatusAnterior(),
                
                m.getStatus(),
                m.getObservaciones()
            });
        }
    }

    private void filtrarTabla() {

        String texto =
                txtBuscar.getText()
                        .toLowerCase();

        modelo.setRowCount(0);

        for (Movimiento m : listaMovimientos) {

            if ((m.getNumeroInventario() != null
                    && m.getNumeroInventario()
                            .toLowerCase()
                            .contains(texto))

                    || (m.getNombreEquipo() != null
                            && m.getNombreEquipo()
                                    .toLowerCase()
                                    .contains(texto))

                    || (m.getAreaAnterior() != null
                            && m.getAreaAnterior()
                                    .toLowerCase()
                                    .contains(texto))

                    || (m.getAreaNueva() != null
                            && m.getAreaNueva()
                                    .toLowerCase()
                                    .contains(texto))

                    || (m.getResguardanteAnterior() != null
                            && m.getResguardanteAnterior()
                                    .toLowerCase()
                                    .contains(texto))

                    || (m.getResguardanteNuevo() != null
                            && m.getResguardanteNuevo()
                                    .toLowerCase()
                                    .contains(texto))

                    || (m.getTipoMovimiento() != null
                            && m.getTipoMovimiento()
                                    .toLowerCase()
                                    .contains(texto))

                    || (m.getObservaciones() != null
                            && m.getObservaciones()
                                    .toLowerCase()
                                    .contains(texto))) {

                modelo.addRow(new Object[] {

                        m.getFechaMovimiento(),

                        m.getNumeroInventario(),

                        m.getNombreEquipo(),

                        m.getAreaAnterior(),

                        m.getAreaNueva(),

                        m.getResguardanteAnterior(),

                        m.getResguardanteNuevo(),

                        m.getTipoMovimiento(),

                        m.getStatusAnterior(),

                        m.getStatus(),

                        m.getObservaciones()
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