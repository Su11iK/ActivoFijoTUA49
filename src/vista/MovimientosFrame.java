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

public class MovimientosFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtBuscar;
    private List<Movimiento> listaMovimientos;

    public MovimientosFrame() {

        setTitle("Movimientos");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();

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
}