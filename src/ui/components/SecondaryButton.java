package ui.components;

import ui.theme.AppColors;
import ui.theme.AppFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SecondaryButton extends JButton {

    private Color backgroundColor = AppColors.BUTTON_SECONDARY;

    private final Color hoverColor = AppColors.BUTTON_SECONDARY_HOVER;

    public SecondaryButton(String text) {

        super(text);

        configurar();

        eventos();

    }

    private void configurar() {

        setFont(AppFonts.BUTTON);

        setForeground(AppColors.TEXT_LIGHT);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setFocusPainted(false);

        setBorderPainted(false);

        setContentAreaFilled(false);

        setOpaque(false);

        setBorder(new EmptyBorder(10,20,10,20));

    }

    private void eventos() {

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                backgroundColor = hoverColor;

                repaint();

            }

            @Override
            public void mouseExited(MouseEvent e) {

                backgroundColor = AppColors.BUTTON_SECONDARY;

                repaint();

            }

        });

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fondo

        g2.setColor(backgroundColor);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                14,
                14
        );

        // Borde

        g2.setColor(AppColors.BORDER);

        g2.setStroke(new BasicStroke(1.2f));

        g2.drawRoundRect(
                0,
                0,
                getWidth()-1,
                getHeight()-1,
                14,
                14
        );

        g2.dispose();

        super.paintComponent(g);

    }

    @Override
    protected void paintBorder(Graphics g) {

        // Sin borde Swing

    }

}