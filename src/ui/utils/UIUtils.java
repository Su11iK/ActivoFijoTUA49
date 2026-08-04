package ui.utils;

import ui.theme.AppColors;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class UIUtils {

    private UIUtils() {
        // Evita instancias
    }

    /**
     * Centra una ventana en la pantalla.
     */
    public static void centerWindow(Window window) {

        window.setLocationRelativeTo(null);

    }

    /**
     * Aplica el color de fondo general.
     */
    public static void applyBackground(Container container) {

        container.setBackground(AppColors.BACKGROUND);

    }

    /**
     * Crea un margen uniforme.
     */
    public static Border createPadding() {

        return new EmptyBorder(15, 15, 15, 15);

    }

    /**
     * Crea un margen personalizado.
     */
    public static Border createPadding(int top, int left, int bottom, int right) {

        return new EmptyBorder(top, left, bottom, right);

    }

    /**
     * Espacio vertical.
     */
    public static Component verticalSpace(int pixels) {

        return Box.createVerticalStrut(pixels);

    }

    /**
     * Espacio horizontal.
     */
    public static Component horizontalSpace(int pixels) {

        return Box.createHorizontalStrut(pixels);

    }

    /**
     * Tamaño fijo.
     */
    public static void setFixedSize(JComponent component, int width, int height) {

        Dimension dimension = new Dimension(width, height);

        component.setPreferredSize(dimension);

        component.setMinimumSize(dimension);

        component.setMaximumSize(dimension);

    }

    /**
     * Tamaño preferido.
     */
    public static void setPreferredSize(JComponent component, int width, int height) {

        component.setPreferredSize(new Dimension(width, height));

    }

    /**
     * Cursor de mano.
     */
    public static void handCursor(JComponent component) {

        component.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

    }

    /**
     * Cursor normal.
     */
    public static void defaultCursor(JComponent component) {

        component.setCursor(
                Cursor.getDefaultCursor()
        );

    }

}