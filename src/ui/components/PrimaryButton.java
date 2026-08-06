package ui.components;

import ui.theme.AppColors;
import ui.theme.AppFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrimaryButton extends JButton {

    private Color backgroundColor = AppColors.BUTTON_PRIMARY;
    private Color hoverColor = AppColors.BUTTON_PRIMARY_HOVER;
    private Color mouse = AppColors.BUTTON_PRIMARY;

    public PrimaryButton(String text) {

        super(text);

        configurarBoton();
        agregarEventos();
    }

    private void configurarBoton() {

        setFont(AppFonts.BUTTON);

        setForeground(AppColors.TEXT_LIGHT);

        setBackground(backgroundColor);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setFocusPainted(false);

        setBorderPainted(false);

        setContentAreaFilled(false);

        setOpaque(false);

        setBorder(new EmptyBorder(10,20,10,20));

    }

    private void agregarEventos() {

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                backgroundColor = hoverColor;
                repaint();

            }

            @Override
            public void mouseExited(MouseEvent e) {

                backgroundColor = mouse;
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

        g2.setColor(backgroundColor);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                14,
                14
        );

        super.paintComponent(g2);

        g2.dispose();

    }

    @Override
    protected void paintBorder(Graphics g) {

        // Sin borde

    }

    public void setButtonColor(Color color) {

        this.backgroundColor = color;

        repaint();
    }

    public Color getButtonColor() {

        return backgroundColor;
    }

    public void setHoverColor(Color color) {

        this.hoverColor = color;
    }

    public void setMouseExited(Color color) {

        this.mouse = color;
    }

}