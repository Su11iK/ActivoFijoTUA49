package vista;

import dao.BienDAO;
import modelo.Bien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Principal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;

    public Principal() {
        setTitle("Sistema de Inventario");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // 🔥 Pantalla completa
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =========================
        // 🔹 PANEL SUPERIOR
        // =========================
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton btnMovimientos = new JButton("Movimientos");
        JButton btnBajas = new JButton("Bajas");

        panelSuperior.add(btnMovimientos);
        panelSuperior.add(btnBajas);

        add(panelSuperior, BorderLayout.NORTH);

        // =========================
        // 🔹 TABLA
        // =========================
        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Inventario");
        modelo.addColumn("Descripción");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Serie");
        modelo.addColumn("Estado");
        modelo.addColumn("Factura");
        modelo.addColumn("Proveedor");
        modelo.addColumn("Tipo Bien");
        modelo.addColumn("Área");
        modelo.addColumn("Resguardante");
        modelo.addColumn("Fecha Alta");
        modelo.addColumn("Estatus");

        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        add(scroll, BorderLayout.CENTER);

        // =========================
        // 🔹 PANEL INFERIOR PRINCIPAL
        // =========================
        JPanel contenedorInferior = new JPanel();
        contenedorInferior.setLayout(new BoxLayout(contenedorInferior, BoxLayout.Y_AXIS));

        // 🔸 CRUD
        JPanel panelCRUD = new JPanel();

        JButton btnAlta = new JButton("Alta");
        JButton btnEditar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Baja");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnAsignar = new JButton("Asignar Resguardante");
        
        panelCRUD.add(btnAlta);
        panelCRUD.add(btnEditar);
        panelCRUD.add(btnEliminar);
        panelCRUD.add(btnBuscar);
        panelCRUD.add(btnAsignar);

        btnAlta.addActionListener(e -> {
            AltaBien alta = new AltaBien(this);
            alta.setVisible(true);

            cargarDatos();
        });

        // 🔸 Catálogos
        JPanel panelCatalogos = new JPanel();

        JButton btnAreas = new JButton("Áreas");
        JButton btnResguardantes = new JButton("Resguardantes");

        panelCatalogos.add(btnAreas);
        panelCatalogos.add(btnResguardantes);

        contenedorInferior.add(panelCRUD);
        contenedorInferior.add(panelCatalogos);

        add(contenedorInferior, BorderLayout.SOUTH);

        // =========================
        cargarDatos();
    }

    private void cargarDatos() {
        BienDAO dao = new BienDAO();
        List<Bien> lista = dao.listarBienes();

        modelo.setRowCount(0);

        for (Bien b : lista) {
            modelo.addRow(new Object[]{
                    b.getId(),
                    b.getNumeroInventario(),
                    b.getDescripcion(),
                    b.getMarca(),
                    b.getModelo(),
                    b.getNumeroSerie(),
                    b.getEstadoFisico(),
                    b.getFactura(),
                    b.getProveedor(),
                    b.getTipoBien(),
                    b.getArea(),
                    b.getResguardante(),
                    b.getFechaAlta(),
                    b.getStatus()
            });
        }
    }
}