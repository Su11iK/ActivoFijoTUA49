package vista;

import dao.MovimientoDAO;
import modelo.Movimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MovimientosFrame extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

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
        modelo.addColumn("Observaciones");

        tabla = new JTable(modelo);

        tabla.setAutoCreateRowSorter(true);

        JPanel panelSuperior = new JPanel();

        JButton btnBienes =
                new JButton("Bienes");

        JButton btnBajas =
                new JButton("Bajas");
        
        panelSuperior.add(btnBienes);
        panelSuperior.add(btnBajas);

        add(panelSuperior,
            BorderLayout.NORTH);

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

        btnBajas.addActionListener(e -> {

            BajasFrame frame =
                    new BajasFrame();

            frame.setVisible(true);

            dispose();
        });

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

        for(Movimiento m :
                listaMovimientos) {

            modelo.addRow(new Object[]{

                m.getFechaMovimiento(),

                m.getNumeroInventario(),

                m.getUsuario(),

                m.getAreaAnterior(),
                m.getAreaNueva(),

                m.getResguardanteAnterior(),
                m.getResguardanteNuevo(),

                m.getTipoMovimiento(),

                m.getObservaciones()
            });
        }
    }
}