package vista;

import dao.BienDAO;
import dao.ResguardanteDAO;
import modelo.Bien;
import modelo.Resguardante;
import ui.components.PrimaryButton;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class AsignarResguardante extends JDialog {

    private JComboBox<Resguardante> cbResguardantes;

    private JLabel lblPuesto;
    private JLabel lblArea;

    private JTextArea txtObservaciones;

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

        setSize(500, 400);

        setLocationRelativeTo(parent);

        setLayout(new GridLayout(0, 1, 5, 5));

        // =========================
        // COMBO
        // =========================
        add(new JLabel("Resguardante:"));

        cbResguardantes =
                new JComboBox<>();

        ResguardanteDAO dao =
                new ResguardanteDAO();

        List<Resguardante> lista =
                dao.listarActivos();

        for (Resguardante r : lista) {
            cbResguardantes.addItem(r);
        }

        add(cbResguardantes);

        // =========================
        // PUESTO
        // =========================
        lblPuesto =
                new JLabel("Puesto:");

        add(lblPuesto);

        // =========================
        // AREA
        // =========================
        lblArea =
                new JLabel("Área:");

        add(lblArea);

        // =========================
        // OBSERVACIONES
        // =========================
        add(new JLabel("Observaciones:"));

        txtObservaciones =
                new JTextArea(5, 20);

        add(new JScrollPane(txtObservaciones));

        // =========================
        // BOTONES
        // =========================
        PrimaryButton btnAsignar =
                new PrimaryButton("Asignar");

        PrimaryButton btnCancelar =
                new PrimaryButton("Cancelar");

        add(btnAsignar);
        add(btnCancelar);

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

        Resguardante r =
                (Resguardante)
                        cbResguardantes.getSelectedItem();

        if (r == null) return;

        if (r.getIdArea() == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "El resguardante seleccionado no tiene un área asignada.\n"
                        + "Asigne primero un área al resguardante.",
                        "Área no asignada",
                        JOptionPane.WARNING_MESSAGE
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

                JOptionPane.showMessageDialog(
                        this,
                        "No se registro ningun cambio de resguardo.",
                        "Sin cambios",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Resguardante asignado a "
                + cambios
                + " bienes."
        );

        dispose();
    }
}