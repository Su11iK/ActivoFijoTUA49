package vista;

import dao.BienDAO;
import dao.ResguardanteDAO;
import modelo.Bien;
import modelo.Resguardante;
import reports.CedulaCensal;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;
import ui.components.SecondaryButton;
import ui.theme.AppColors;
import ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class ReportesFrame extends JDialog {

    private JComboBox<String> cbReporte;
    private JComboBox<Resguardante> cbResguardantes;
    private String reporte;
    private List<Bien> listaBienes;

    public ReportesFrame(JFrame parent) {

        super(parent,
                "Generación de Reportes",
                true);

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 1, 5, 5));
        ((JComponent) getContentPane()).setBorder(
                UIUtils.createPadding(25, 25, 20, 20)
        );
        getContentPane().setBackground(AppColors.PRIMARY_LIGHT);

        // =========================
        // COMBO
        // =========================

        cbReporte = new JComboBox<>();
        cbReporte.addItem("");
        cbReporte.addItem("Resumen General de Inventario");
        cbReporte.addItem("Cedula Censal por Usuario");
        cbReporte.addItem("Cedula de Eq. de Computo");

        cbResguardantes =
                new JComboBox<>();

        ResguardanteDAO dao =
                new ResguardanteDAO();

        List<Resguardante> lista =
                dao.listarActivos();

        for (Resguardante r : lista) {
            cbResguardantes.addItem(r);
        }

        // =========================
        // BOTONES
        // =========================
        PrimaryButton btnGenerar = new PrimaryButton("Generar Excel");
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");

        RoundedPanel card = new RoundedPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(0, 1, 5, 5));
        card.setBorder(UIUtils.createPadding(25,25,25,25));

        card.add(new JLabel("Tipo de Reporte:"));
        card.add(cbReporte);
        card.add(new JLabel("Resguardante:"));
        card.add(cbResguardantes);
        card.add(btnGenerar);
        card.add(btnCancelar);
        add(card);

        BienDAO bdao = new BienDAO();
        listaBienes = bdao.listarBienes();

        // =========================
        // EVENTOS
        // =========================

        btnGenerar.addActionListener(
                e -> generar());

        btnCancelar.addActionListener(
                e -> dispose());
    }

    private void generar() {

        reporte = cbReporte.getSelectedItem().toString();
        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        Resguardante r =
                (Resguardante)
                        cbResguardantes.getSelectedItem();

        if (r == null) return;

        if (r.getIdArea() == 0) {

                mensaje.setText("El resguardante seleccionado no tiene un área asignada.\n"
                        + " Asigne primero un área al resguardante.");
                JOptionPane.showMessageDialog(
                        this,
                        panel
                );

                return;
        }

        if (reporte.equals("Cedula Censal por Usuario")) {
            try {

                File archivo =
                        CedulaCensal.generar(
                                listaBienes,
                                r.getNombre(),
                                r.getNombreArea(),
                                System.getProperty("user.home")
                                + File.separator
                                + "Desktop"
                                + File.separator
                                + "Cedula_Censal.xlsx"
                        );

                    JOptionPane.showMessageDialog(
                            this,
                            "Cédula generada correctamente."
                    );

                    Desktop.getDesktop().open(
                            archivo
                    );

                } catch (Exception ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(
                            this,
                            "Error al generar la cédula:\n"
                            + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
            }
        }
    }

}