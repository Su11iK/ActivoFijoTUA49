package ui.components;

import ui.theme.AppColors;
import ui.theme.AppFonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class SearchField extends JTextField {

    private boolean focused = false;

    private final int radius = 14;

    public SearchField() {

        configurar();

    }

    public SearchField(String text) {
        super(text);
        configurar();
    }

    private void configurar() {

        setFont(AppFonts.TEXT_FIELD);

        setForeground(AppColors.TEXT_PRIMARY);

        setBackground(Color.WHITE);

        setCaretColor(AppColors.PRIMARY);

        setBorder(new EmptyBorder(8, 12, 8, 12));

        setOpaque(false);

        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                focused = true;

                repaint();

            }

            @Override
            public void focusLost(FocusEvent e) {

                focused = false;

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

        g2.setColor(Color.WHITE);

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radius,
                radius
        );

        super.paintComponent(g);

        g2.dispose();

    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        if (focused) {

            g2.setColor(AppColors.BORDER_FOCUS);

        } else {

            g2.setColor(AppColors.BORDER);

        }

        g2.setStroke(new BasicStroke(1.5f));

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                radius,
                radius
        );

        g2.dispose();

    }

}