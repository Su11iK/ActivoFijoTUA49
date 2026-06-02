package vista;

import dao.BienDAO;
import modelo.Bien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Principal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private List<Bien> listaBienes;

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
        tabla.setSelectionMode(
            ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );
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

        btnEditar.addActionListener(e -> {

            int[] filas = tabla.getSelectedRows();

            if (filas.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "Seleccione al menos un bien");

                return;
            }

            // 🔥 UN SOLO BIEN
            if (filas.length == 1) {

                Bien bienSeleccionado =
                        listaBienes.get(filas[0]);

                ActualizarBien actualizar =
                        new ActualizarBien(this, bienSeleccionado);

                actualizar.setVisible(true);

            } else {

                // 🔥 MULTIPLE
                ActualizarMultiple multiple =
                        new ActualizarMultiple(this, filas, listaBienes);

                multiple.setVisible(true);
            }

            cargarDatos();
        });

        btnEliminar.addActionListener(e -> {

            int[] filas =
                    tabla.getSelectedRows();

            if (filas.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "Seleccione al menos un bien");

                return;
            }

            BajaBien baja =
                    new BajaBien(
                            this,
                            filas,
                            listaBienes
                    );

            baja.setVisible(true);

            cargarDatos();
        });

        btnAsignar.addActionListener(e -> {

            int[] filas =
                    tabla.getSelectedRows();

            if (filas.length == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione al menos un bien"
                );

                return;
            }

            AsignarResguardante ar =
                    new AsignarResguardante(
                            this,
                            filas,
                            listaBienes
                    );

            ar.setVisible(true);

            cargarDatos();
        });

        // 🔸 Catálogos
        JPanel panelCatalogos = new JPanel();

        JButton btnAreas = new JButton("Áreas");
        JButton btnResguardantes = new JButton("Resguardantes");

        panelCatalogos.add(btnAreas);
        panelCatalogos.add(btnResguardantes);

        btnAreas.addActionListener(e -> {

            AreasFrame areas =
                    new AreasFrame(this);

            areas.setVisible(true);

            cargarDatos();
        });

        btnResguardantes.addActionListener(e -> {

            ResguardantesFrame frame =
                    new ResguardantesFrame(this);

            frame.setVisible(true);

            cargarDatos();
        });

        contenedorInferior.add(panelCRUD);
        contenedorInferior.add(panelCatalogos);

        add(contenedorInferior, BorderLayout.SOUTH);

        // =========================
        cargarDatos();
    }

    private void cargarDatos() {
        BienDAO dao = new BienDAO();
        listaBienes = dao.listarBienes();

        modelo.setRowCount(0);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Bien b : listaBienes) {
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
                    formato.format(b.getFechaAlta()),
                    b.getStatus()
            });
        }

        
    }
}