package ui.components;

import ui.theme.AppColors;
import ui.theme.AppFonts;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    private JLabel lblLogo;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;

    public HeaderPanel() {

        inicializarComponentes();

        construir();

    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        setBackground(AppColors.PRIMARY_DARK);

        setBorder(BorderFactory.createMatteBorder(
                0,
                0,
                1,
                0,
                AppColors.BORDER
        ));

        lblLogo = new JLabel();

        lblTitulo = new JLabel("Sistema de Inventario de Activos Fijos");

        lblSubtitulo = new JLabel("Tribunal Unitario Agrario Distrito 49");

        lblTitulo.setFont(AppFonts.TITLE);

        lblTitulo.setForeground(AppColors.TEXT_LIGHT);

        lblSubtitulo.setFont(AppFonts.SMALL);

        lblSubtitulo.setForeground(AppColors.TEXT_LIGHT);

    }

    private void construir() {

        JPanel izquierda = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                15,
                12
        ));

        izquierda.setOpaque(false);

        ImageIcon logo = new ImageIcon(
                getClass().getResource("/images/tua49v.png")
        );

        Image imagen = logo.getImage().getScaledInstance(
                48,
                48,
                Image.SCALE_SMOOTH
        );

        lblLogo.setIcon(new ImageIcon(imagen));

        JPanel textos = new JPanel();

        textos.setOpaque(false);

        textos.setLayout(new BoxLayout(
                textos,
                BoxLayout.Y_AXIS
        ));

        textos.add(lblTitulo);

        textos.add(Box.createVerticalStrut(3));

        textos.add(lblSubtitulo);

        izquierda.add(lblLogo);

        izquierda.add(textos);

        add(izquierda, BorderLayout.WEST);

    }

}