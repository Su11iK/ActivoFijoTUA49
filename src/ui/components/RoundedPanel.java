package ui.components;

import ui.theme.AppColors;

import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {

    private int radius = 18;

    private Color backgroundColor = AppColors.CARD;

    public RoundedPanel() {

        setOpaque(false);

    }

    public RoundedPanel(LayoutManager layout) {

        super(layout);

        setOpaque(false);

    }

    public RoundedPanel(int radius) {

        this.radius = radius;

        setOpaque(false);

    }

    public void setRadius(int radius) {

        this.radius = radius;

        repaint();

    }

    public int getRadius() {

        return radius;

    }

    @Override
    public void setBackground(Color bg) {

        backgroundColor = bg;

        repaint();

    }

    @Override
    public Color getBackground() {

        return backgroundColor;

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        /*
         * Panel
         */

        g2.setColor(backgroundColor);

        g2.fillRoundRect(
                0,
                0,
                getWidth()-8,
                getHeight()-8,
                radius,
                radius
        );

        g2.dispose();

        super.paintComponent(g);

    }

}