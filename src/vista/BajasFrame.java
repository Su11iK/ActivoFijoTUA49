package vista;

import dao.BajaDAO;
import modelo.Baja;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BajasFrame extends JFrame {

    private JTable tabla;

    private DefaultTableModel modelo;

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

        JPanel panelSuperior =
                new JPanel();

        JButton btnBienes =
                new JButton("Bienes");

        JButton btnMovimientos =
                new JButton("Movimientos");

        panelSuperior.add(btnBienes);

        panelSuperior.add(btnMovimientos);

        add(panelSuperior,
                BorderLayout.NORTH);

        // =========================
        // TABLA
        // =========================

        modelo =
                new DefaultTableModel();

        modelo.addColumn("Inventario");

        modelo.addColumn("Usuario");

        modelo.addColumn("Fecha Baja");

        modelo.addColumn("Motivo");

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

        for(Baja b : listaBajas) {

            modelo.addRow(new Object[]{

                    b.getNumeroInventario(),

                    b.getUsuario(),

                    b.getFechaBaja(),

                    b.getMotivo()
            });
        }
    }
}