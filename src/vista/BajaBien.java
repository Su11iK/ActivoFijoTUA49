package vista;

import dao.BienDAO;

import javax.swing.*;
import java.awt.*;

public class BajaBien extends JDialog {

    private JTextArea txtMotivo;

    private int[] filas;
    private java.util.List<modelo.Bien> listaBienes;

    public BajaBien(
            JFrame parent,
            int[] filas,
            java.util.List<modelo.Bien> listaBienes
    ) {

        super(parent, "Baja de Bien", true);

        this.filas = filas;
        this.listaBienes = listaBienes;

        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JLabel lbl =
                new JLabel("Observaciones:");

        add(lbl, BorderLayout.NORTH);

        txtMotivo = new JTextArea();

        add(new JScrollPane(txtMotivo),
                BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();

        JButton btnAceptar =
                new JButton("Dar de Baja");

        JButton btnCancelar =
                new JButton("Cancelar");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.SOUTH);

        // =========================
        // EVENTOS
        // =========================
        btnAceptar.addActionListener(e -> baja());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void baja() {

        String motivo =
                txtMotivo.getText().trim();

        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        "¿Seguro de dar de baja?",
                        "Confirmar",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        BienDAO dao = new BienDAO();

        for (int fila : filas) {

                int idBien =
                        listaBienes.get(fila).getId();

                String status =
                        listaBienes.get(fila).getStatus();

                String areNueva =
                        listaBienes.get(fila).getArea();

                String resNueva =
                        listaBienes.get(fila).getResguardante();

                dao.darBajaBien(
                        idBien,
                        1,
                        motivo,
                        status,
                        areNueva,
                        resNueva
                );
        }

        JOptionPane.showMessageDialog(this,
                "Baja realizada");

        dispose();
    }
}