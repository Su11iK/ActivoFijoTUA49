package vista;

import dao.BienDAO;
import modelo.Bien;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.event.DocumentEvent;

public class Principal extends JFrame {

    private JTable tabla;
    private DefaultTableModel modelo;
    private List<Bien> listaBienes;
    private JTextField txtBuscar;

    public Principal() {
        setTitle("Sistema de Inventario");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // 🔥 Pantalla completa
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =========================
        // 🔹 PANEL SUPERIOR
        // =========================

        JButton btnMovimientos = new JButton("Movimientos");
        JButton btnBajas = new JButton("Bajas");

        JPanel panelBotonesSuperior = new JPanel();

        panelBotonesSuperior.add(btnMovimientos);
        panelBotonesSuperior.add(btnBajas);

        btnMovimientos.addActionListener(e -> {

            MovimientosFrame frame =
                    new MovimientosFrame();

            frame.setVisible(true);

            dispose();
        });

        btnBajas.addActionListener(e -> {

            BajasFrame frame =
                    new BajasFrame();

            frame.setVisible(true);

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

        // =========================
        // 🔹 TABLA
        // =========================
        modelo = new DefaultTableModel();

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
        tabla.setAutoCreateRowSorter(true);
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
        JButton btnAsignar = new JButton("Asignar Resguardante");
        JButton btnReporte = new JButton("Generar Reporte");
        
        panelCRUD.add(btnAlta);
        panelCRUD.add(btnEditar);
        panelCRUD.add(btnEliminar);
        panelCRUD.add(btnAsignar);
        panelCRUD.add(btnReporte);

        btnAlta.addActionListener(e -> {
            AltaBien alta = new AltaBien(this);
            alta.setVisible(true);

            cargarDatos();
        });

        btnEditar.addActionListener(e -> {

            int[] filasVista =
                    tabla.getSelectedRows();

            int[] filasModelo =
                    new int[filasVista.length];

            for(int i = 0; i < filasVista.length; i++) {

                filasModelo[i] =
                        tabla.convertRowIndexToModel(
                                filasVista[i]
                        );
            }

            if (filasVista.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "Seleccione al menos un bien");

                return;
            }

            // 🔥 UN SOLO BIEN
            if (filasVista.length == 1) {

                Bien bienSeleccionado =
                        listaBienes.get(
                            tabla.convertRowIndexToModel(
                                tabla.getSelectedRow()
                            )
                        );

                ActualizarBien actualizar =
                        new ActualizarBien(this, bienSeleccionado);

                actualizar.setVisible(true);

            } else {

                // 🔥 MULTIPLE
                ActualizarMultiple multiple =
                        new ActualizarMultiple(
                                this,
                                filasModelo,
                                listaBienes
                        );

                multiple.setVisible(true);
            }

            tabla.convertRowIndexToModel(tabla.getSelectedRow());

            cargarDatos();
        });

        btnEliminar.addActionListener(e -> {

            int[] filasVista =
                    tabla.getSelectedRows();

            int[] filasModelo =
                    new int[filasVista.length];

            for(int i = 0; i < filasVista.length; i++) {

                filasModelo[i] =
                        tabla.convertRowIndexToModel(
                                filasVista[i]
                        );
            }

            if (filasVista.length == 0) {

                JOptionPane.showMessageDialog(this,
                        "Seleccione al menos un bien");

                return;
            }

            BajaBien baja =
                    new BajaBien(
                            this,
                            filasModelo,
                            listaBienes
                    );

            baja.setVisible(true);

            cargarDatos();
        });

        btnAsignar.addActionListener(e -> {

            int[] filasVista =
                    tabla.getSelectedRows();

            int[] filasModelo =
                    new int[filasVista.length];

            for(int i = 0; i < filasVista.length; i++) {

                filasModelo[i] =
                        tabla.convertRowIndexToModel(
                                filasVista[i]
                        );
            }

            if (filasVista.length == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione al menos un bien"
                );

                return;
            }

            AsignarResguardante ar =
                    new AsignarResguardante(
                            this,
                            filasModelo,
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

    private void filtrarTabla() {

        String texto =
                txtBuscar.getText().trim();

        BienDAO dao =
                new BienDAO();

        listaBienes =
                dao.buscarBienes(texto);

        modelo.setRowCount(0);

        for(Bien b : listaBienes) {

            modelo.addRow(new Object[]{

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

    private void cargarDatos() {
        BienDAO dao = new BienDAO();
        listaBienes = dao.listarBienes();

        modelo.setRowCount(0);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        for (Bien b : listaBienes) {
            modelo.addRow(new Object[]{
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