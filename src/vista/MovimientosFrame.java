package vista;

import dao.MovimientoDAO;
import modelo.Movimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MovimientosFrame extends JDialog {

    private JTable tabla;
    private DefaultTableModel modelo;

    private List<Movimiento> listaMovimientos;

    public MovimientosFrame(JFrame parent) {

        super(parent,
                "Movimientos",
                true);

        setSize(1400, 700);

        setLocationRelativeTo(parent);

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