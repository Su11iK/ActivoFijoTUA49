package vista;

import dao.BienDAO;
import ui.components.PrimaryButton;
import ui.components.RoundedPanel;
import ui.components.RoundedTextArea;
import ui.components.SecondaryButton;
import ui.theme.AppColors;
import ui.utils.UIUtils;

import javax.swing.*;
import java.awt.*;

public class BajaBien extends JDialog {

    private RoundedTextArea txtMotivo;

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

        setSize(500, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        ((JComponent) getContentPane()).setBorder(
                UIUtils.createPadding(25, 25, 20, 20)
        );
        getContentPane().setBackground(AppColors.PRIMARY_LIGHT);

        JLabel lbl = new JLabel("Observaciones:");

        txtMotivo = new RoundedTextArea();
        JScrollPane scroll = new JScrollPane(txtMotivo);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        RoundedPanel panelBotones = new RoundedPanel();
        panelBotones.setBackground(Color.WHITE);

        PrimaryButton btnAceptar =
                new PrimaryButton("Dar de Baja");

        SecondaryButton btnCancelar =
                new SecondaryButton("Cancelar");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        RoundedPanel card = new RoundedPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new GridLayout(0, 1, 5, 5));
        card.setBorder(UIUtils.createPadding(25,25,25,25));

        card.add(lbl, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        card.add(panelBotones, BorderLayout.SOUTH);
        add(card);

        // =========================
        // EVENTOS
        // =========================
        btnAceptar.addActionListener(e -> baja());

        btnCancelar.addActionListener(e -> dispose());
    }

    private void baja() {

        RoundedPanel panel = new RoundedPanel(
                new GridLayout(0, 1));
        panel.setBackground(Color.WHITE);
        panel.setBorder(UIUtils.createPadding(25,25,25,25));
        JLabel mensaje = new JLabel("");
        panel.add(mensaje);

        String motivo =
                txtMotivo.getText().trim();

        mensaje.setText("¿Seguro de dar de baja?");
        int confirmacion =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
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

        mensaje.setText("Baja realizada");
        JOptionPane.showMessageDialog(this,
                panel);

        dispose();
    }
}