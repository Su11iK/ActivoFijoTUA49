package vista;

import dao.BienDAO;
import dao.ResguardanteDAO;
import modelo.Bien;
import modelo.Resguardante;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;
import ui.components.RoundedTextArea;
import ui.components.SecondaryButton;
import ui.theme.AppColors;
import ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class AsignarResguardante extends JDialog {

    private JComboBox<Resguardante> cbResguardantes;

    private JLabel lblPuesto;
    private JLabel lblArea;

    private RoundedTextArea txtObservaciones;

    private int[] filas;
    private List<Bien> listaBienes;

    public AsignarResguardante(
            JFrame parent,
            int[] filas,
            List<Bien> listaBienes
    ) {

        super(parent,
                "Asignar Resguardante",
                true);

        this.filas = filas;
        this.listaBienes = listaBienes;

        setSize(500, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 1, 5, 5));
        ((JComponent) getContentPane()).setBorder(
                UIUtils.createPadding(25, 25, 20, 20)
        );
        getContentPane().setBackground(AppColors.PRIMARY_LIGHT);

        // =========================
        // COMBO
        // =========================

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
        // PUESTO
        // =========================
        lblPuesto = new JLabel("Puesto:");

        // =========================
        // AREA
        // =========================
        lblArea = new JLabel("Área:");

        // =========================
        // OBSERVACIONES
        // =========================

        txtObservaciones = new RoundedTextArea();
        JScrollPane scroll = new JScrollPane(txtObservaciones);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        // =========================
        // BOTONES
        // =========================
        PrimaryButton btnAsignar = new PrimaryButton("Asignar");
        SecondaryButton btnCancelar = new SecondaryButton("Cancelar");

        RoundedPanel card = new RoundedPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(0, 1, 5, 5));
        card.setBorder(UIUtils.createPadding(25,25,25,25));

        card.add(new JLabel("Resguardante:"));
        card.add(cbResguardantes);
        card.add(lblPuesto);
        card.add(lblArea);
        card.add(new JLabel("Observaciones:"));
        card.add(scroll);
        card.add(btnAsignar);
        card.add(btnCancelar);
        add(card);

        // =========================
        // EVENTOS
        // =========================
        cbResguardantes.addActionListener(
                e -> actualizarInfo());

        btnAsignar.addActionListener(
                e -> asignar());

        btnCancelar.addActionListener(
                e -> dispose());

        actualizarInfo();
    }

    // =========================
    // MOSTRAR INFO
    // =========================
    private void actualizarInfo() {

        Resguardante r =
                (Resguardante)
                        cbResguardantes.getSelectedItem();

        if (r == null) return;

        lblPuesto.setText(
                "Puesto: " + r.getPuesto());

        lblArea.setText(
                "Área: " + r.getNombreArea());
    }

    // =========================
    // ASIGNAR
    // =========================
    private void asignar() {

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

        BienDAO dao = new BienDAO();

        int cambios = 0;

        for (int fila : filas) {

            Bien b =
                    listaBienes.get(fila);

            if (Objects.equals(b.getResguardante(), r.getNombre())) {   
                continue;
            }

            dao.asignarResguardante(
                    b.getId(),
                    r.getId(),
                    r.getIdArea(),
                    1,
                    txtObservaciones.getText(),
                    r.getNombreArea(),
                    r.getNombre()
            );
            cambios++;
        }

        if (cambios == 0) {

                mensaje.setText("No se registro ningun cambio de resguardo");
                JOptionPane.showMessageDialog(
                        this,
                        panel
                );

                return;
        }

        mensaje.setText("Resguardante asignado a "
                + cambios
                + " bien(es)");
        JOptionPane.showMessageDialog(
                this,
                panel
        );

        dispose();
    }
}