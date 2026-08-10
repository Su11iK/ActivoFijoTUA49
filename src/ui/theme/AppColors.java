package ui.theme;

import java.awt.Color;

/**
 * ============================================================
 * Sistema de Inventario TUA49
 * Clase encargada de centralizar toda la paleta de colores.
 *
 * Si en algún momento se desea cambiar la identidad visual,
 * únicamente se modifican estos valores.
 * ============================================================
 */
public final class AppColors {

    private AppColors() {
        // Evita instancias
    }

    // ==========================================================
    // COLORES PRINCIPALES (Basados en el logotipo del TUA49)
    // ==========================================================

    /**
     * Verde institucional.
     */
    public static final Color PRIMARY = new Color(0, 110, 105);

    /**
     * Verde ligeramente más oscuro.
     */
    public static final Color PRIMARY_DARK = new Color(0, 55, 53);

    /**
     * Verde claro para selección.
     */
    public static final Color PRIMARY_LIGHT = new Color(96, 153, 150);

    /**
     * Naranja institucional.
     */
    public static final Color SECONDARY = new Color(242, 106, 42);

    /**
     * Naranja para hover.
     */
    public static final Color SECONDARY_DARK = new Color(220, 88, 27);

    /**
     * Naranja para hover.
     */
    public static final Color SECONDARY_DARK2 = new Color(165, 66, 20);

    /**
     * Verde claro para selección.
     */
    public static final Color SECONDARY_LIGHT = new Color(227, 106, 59);

    /**
     * Verde claro para selección.
     */
    public static final Color SECONDARY_LIGHT2 = new Color(245, 150, 105);

    /**
     * Beige/Dorado del logotipo.
     */
    public static final Color ACCENT = new Color(234, 225, 202);

    // ==========================================================
    // FONDOS
    // ==========================================================

    /**
     * Fondo general de la aplicación.
     */
    public static final Color BACKGROUND = SECONDARY_LIGHT;

    /**
     * Fondo de paneles.
     */
    public static final Color SURFACE = Color.WHITE;

    /**
     * Fondo para tarjetas.
     */
    public static final Color CARD = SECONDARY_LIGHT;

    // ==========================================================
    // TEXTOS
    // ==========================================================

    /**
     * Texto principal.
     */
    public static final Color TEXT_PRIMARY = new Color(40, 40, 40);

    /**
     * Texto secundario.
     */
    public static final Color TEXT_SECONDARY = new Color(110, 110, 110);

    /**
     * Texto deshabilitado.
     */
    public static final Color TEXT_DISABLED = new Color(170, 170, 170);

    /**
     * Texto blanco.
     */
    public static final Color TEXT_LIGHT = Color.WHITE;

    // ==========================================================
    // BORDES
    // ==========================================================

    /**
     * Borde principal.
     */
    public static final Color BORDER = new Color(220, 220, 220);

    /**
     * Borde cuando un componente tiene el foco.
     */
    public static final Color BORDER_FOCUS = PRIMARY;

    // ==========================================================
    // TABLAS
    // ==========================================================

    /**
     * Encabezado.
     */
    public static final Color TABLE_HEADER = PRIMARY;

    /**
     * Texto encabezado.
     */
    public static final Color TABLE_HEADER_TEXT = Color.WHITE;

    /**
     * Filas.
     */
    public static final Color TABLE_ROW = Color.WHITE;

    /**
     * Filas alternadas.
     */
    public static final Color TABLE_ROW_ALTERNATE = ACCENT;

    /**
     * Selección.
     */
    public static final Color TABLE_SELECTION = new Color(182, 155, 73);

    /**
     * Color del grid.
     */
    public static final Color TABLE_GRID = new Color(232, 232, 232);

    // ==========================================================
    // BOTONES
    // ==========================================================

    /**
     * Botón principal.
     */
    public static final Color BUTTON_PRIMARY = PRIMARY;

    /**
     * Hover botón principal.
     */
    public static final Color BUTTON_PRIMARY_HOVER = PRIMARY_DARK;

    /**
     * Botón secundario.
     */
    public static final Color BUTTON_SECONDARY = new Color(200, 0, 0);

    /**
     * Hover botón secundario.
     */
    public static final Color BUTTON_SECONDARY_HOVER = new Color(139, 0, 0);

    // ==========================================================
    // ESTADOS
    // ==========================================================

    /**
     * Correcto.
     */
    public static final Color SUCCESS = new Color(46, 125, 50);

    /**
     * Advertencia.
     */
    public static final Color WARNING = new Color(247, 166, 100);

    /**
     * Error.
     */
    public static final Color ERROR = new Color(211, 47, 47);

    /**
     * Información.
     */
    public static final Color INFO = new Color(2, 136, 209);

}