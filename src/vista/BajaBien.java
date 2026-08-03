package vista;

import dao.BienDAO;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;

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

        RoundedPanel panelBotones = new RoundedPanel();

        PrimaryButton btnAceptar =
                new PrimaryButton("Dar de Baja");

        PrimaryButton btnCancelar =
                new PrimaryButton("Cancelar");

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

                String areAnt =
                        listaBienes.get(fila).getArea();
                
                if (areAnt == "" || areAnt == null) {
                        areAnt = "<vacío>";
                }

                String resAnt =
                        listaBienes.get(fila).getResguardante();

                if (resAnt == "" || resAnt == null) {
                        resAnt = "<vacío>";
                }

                dao.darBajaBien(
                        idBien,
                        1,
                        motivo,
                        status,
                        areAnt,
                        resAnt
                );
        }

        JOptionPane.showMessageDialog(this,
                "Baja realizada");

        dispose();
    }
}