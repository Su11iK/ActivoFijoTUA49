package vista;

import dao.BienDAO;
import modelo.Bien;
import ui.components.HeaderPanel;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;
import ui.components.SearchField;
import ui.components.TableStyle;
import ui.theme.AppColors;
import ui.utils.UIUtils;

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
    private SearchField txtBuscar;

    public Principal() {
        setTitle("Sistema de Inventario");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // 🔥 Pantalla completa
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // =========================
        // 🔹 PANEL SUPERIOR
        // =========================

        PrimaryButton btnMovimientos = new PrimaryButton("Movimientos");
        PrimaryButton btnBajas = new PrimaryButton("Bajas");

        RoundedPanel panelBotonesSuperior = new RoundedPanel();

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

        RoundedPanel panelSuperior = new RoundedPanel(new BorderLayout());
        panelSuperior.setBorder(
                UIUtils.createPadding(0, 25, 0, 20)
        );

        JLabel buscar = new JLabel("Buscar: ");
        buscar.setForeground(AppColors.TEXT_LIGHT);
        panelSuperior.add(
                buscar,
                BorderLayout.WEST
        );

        txtBuscar = new SearchField();
        txtBuscar.setColumns(25);

        panelSuperior.add(
                txtBuscar,
                BorderLayout.CENTER
        );

        HeaderPanel header = new HeaderPanel();

        JPanel panelNorte = new JPanel();
        panelNorte.setLayout(new BoxLayout(panelNorte, BoxLayout.Y_AXIS));
        panelNorte.setOpaque(false);

        panelNorte.add(header);
        panelNorte.add(panelBotonesSuperior);
        panelNorte.add(panelSuperior);

        add(panelNorte, BorderLayout.NORTH);

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
        modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                        return false;
                }
        };

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
        TableStyle.apply(tabla);
        tabla.setAutoCreateRowSorter(true);
        tabla.setSelectionMode(
            ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );
        JScrollPane scroll = new JScrollPane(tabla);

        scroll.setBorder(
                UIUtils.createPadding(0, 25, 0, 20)
        );

        add(scroll, BorderLayout.CENTER);

        // =========================
        // 🔹 PANEL INFERIOR PRINCIPAL
        // =========================
        RoundedPanel contenedorInferior = new RoundedPanel();
        contenedorInferior.setLayout(new BoxLayout(contenedorInferior, BoxLayout.Y_AXIS));

        // 🔸 CRUD
        RoundedPanel panelCRUD = new RoundedPanel();

        PrimaryButton btnAlta = new PrimaryButton("Alta");
        PrimaryButton btnEditar = new PrimaryButton("Actualizar");
        PrimaryButton btnEliminar = new PrimaryButton("Baja");
        PrimaryButton btnAsignar = new PrimaryButton("Asignar Resguardante");
        PrimaryButton btnReporte = new PrimaryButton("Generar Reporte");
        
        panelCRUD.add(btnAlta);
        panelCRUD.add(btnEditar);
        panelCRUD.add(btnEliminar);
        panelCRUD.add(btnAsignar);
        panelCRUD.add(btnReporte);

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

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

                mensaje.setText("Seleccione al menos un bien");
                JOptionPane.showMessageDialog(this,
                        panel);

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

                mensaje.setText("Seleccione al menos un bien");
                JOptionPane.showMessageDialog(this,
                        panel);

                return;
            }

            for (int fila : filasModelo) {
                Bien bien = listaBienes.get(fila);

                if ("BAJA".equalsIgnoreCase(bien.getStatus())) {
                mensaje.setText("Uno o más bienes seleccionados ya se encuentran dados de baja");
                JOptionPane.showMessageDialog(this,
                        panel);
                return;
                }
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

                mensaje.setText("Seleccione al menos un bien");
                JOptionPane.showMessageDialog(
                        this,
                        panel
                );

                return;
            }

            for (int fila : filasModelo) {
                Bien bien = listaBienes.get(fila);

                if ("BAJA".equalsIgnoreCase(bien.getStatus())) {
                mensaje.setText("No se permite en bienes que ya se encuentran dados de baja");
                JOptionPane.showMessageDialog(this,
                        panel);
                return;
                }
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

        btnReporte.addActionListener(e -> {
            ReportesFrame reporte = new ReportesFrame(this);
            reporte.setVisible(true);

            cargarDatos();
        });

        // 🔸 Catálogos
        RoundedPanel panelCatalogos = new RoundedPanel();

        PrimaryButton btnAreas = new PrimaryButton("Áreas");
        PrimaryButton btnResguardantes = new PrimaryButton("Resguardantes");

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