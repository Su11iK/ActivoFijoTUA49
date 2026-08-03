package ui.theme;

import java.awt.Font;

/**
 * ============================================================
 * Sistema de Inventario TUA49
 * Clase encargada de centralizar todas las fuentes del sistema.
 * ============================================================
 */
public final class AppFonts {

    private static final String FONT_NAME = "Segoe UI";

    private AppFonts() {
        // Evita instancias
    }

    // ==========================================================
    // TÍTULOS
    // ==========================================================

    public static final Font TITLE_LARGE =
            new Font(FONT_NAME, Font.BOLD, 24);

    public static final Font TITLE =
            new Font(FONT_NAME, Font.BOLD, 20);

    public static final Font SUBTITLE =
            new Font(FONT_NAME, Font.BOLD, 16);

    // ==========================================================
    // TEXTO NORMAL
    // ==========================================================

    public static final Font REGULAR =
            new Font(FONT_NAME, Font.PLAIN, 14);

    public static final Font MEDIUM =
            new Font(FONT_NAME, Font.PLAIN, 15);

    public static final Font BOLD =
            new Font(FONT_NAME, Font.BOLD, 14);

    // ==========================================================
    // COMPONENTES
    // ==========================================================

    public static final Font BUTTON =
            new Font(FONT_NAME, Font.BOLD, 14);

    public static final Font TABLE =
            new Font(FONT_NAME, Font.PLAIN, 13);

    public static final Font TABLE_HEADER =
            new Font(FONT_NAME, Font.BOLD, 13);

    public static final Font LABEL =
            new Font(FONT_NAME, Font.PLAIN, 14);

    public static final Font TEXT_FIELD =
            new Font(FONT_NAME, Font.PLAIN, 14);

    public static final Font COMBO =
            new Font(FONT_NAME, Font.PLAIN, 14);

    public static final Font SMALL =
            new Font(FONT_NAME, Font.PLAIN, 12);

}