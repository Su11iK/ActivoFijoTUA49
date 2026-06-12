package vista;

import dao.BienDAO;
import dao.ResguardanteDAO;
import modelo.Bien;
import modelo.Resguardante;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
        JButton btnAsignar =
                new JButton("Asignar");

        JButton btnCancelar =
                new JButton("Cancelar");

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

        BienDAO dao = new BienDAO();

        for (int fila : filas) {

            Bien b =
                    listaBienes.get(fila);

            dao.asignarResguardante(
                    b.getId(),
                    r.getId(),
                    r.getIdArea(),
                    1,
                    txtObservaciones.getText(),
                    r.getNombreArea(),
                    r.getNombre()
            );
        }

        JOptionPane.showMessageDialog(
                this,
                "Resguardante asignado");

        dispose();
    }
}