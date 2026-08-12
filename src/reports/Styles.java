package reports;

import java.awt.Color;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class Styles {

    // ============================================================
    // COLORES DEL REPORTE
    // ============================================================

    /*
     * Color institucional principal.
     * Puedes modificarlo posteriormente para hacerlo coincidir
     * exactamente con AppColors del sistema.
     */
    private static final Color COLOR_PRINCIPAL =
            new Color(0, 119, 115);

    private static final Color COLOR_SECUNDARIO =
            new Color(91, 159, 157);

    private static final Color COLOR_FONDO_TITULO =
            new Color(240, 245, 245);

    private static final Color COLOR_BORDE =
            new Color(190, 190, 190);

    private static final Color COLOR_TEXTO =
            new Color(40, 40, 40);

    private static final Color COLOR_BLANCO =
            Color.WHITE;


    // ============================================================
    // TÍTULO PRINCIPAL
    // ============================================================

    public static CellStyle titulo(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        font.setColor(
                new XSSFColor(COLOR_PRINCIPAL, null)
        );

        style.setFont(font);

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        return style;
    }


    // ============================================================
    // SUBTÍTULO
    // ============================================================

    public static CellStyle subtitulo(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setColor(
                new XSSFColor(COLOR_TEXTO, null)
        );

        style.setFont(font);

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        return style;
    }


    // ============================================================
    // INFORMACIÓN DEL REPORTE
    // ============================================================

    public static CellStyle informacion(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setColor(
                new XSSFColor(COLOR_TEXTO, null)
        );

        style.setFont(font);

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        return style;
    }


    // ============================================================
    // ENCABEZADOS DE LA TABLA
    // ============================================================

    public static CellStyle encabezado(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setColor(
                new XSSFColor(COLOR_BLANCO, null)
        );

        style.setFont(font);

        style.setFillForegroundColor(
                new XSSFColor(COLOR_PRINCIPAL, null)
        );

        style.setFillPattern(
                FillPatternType.SOLID_FOREGROUND
        );

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        agregarBordes(style);

        return style;
    }


    // ============================================================
    // CELDAS NORMALES
    // ============================================================

    public static CellStyle celda(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setColor(
                new XSSFColor(COLOR_TEXTO, null)
        );

        style.setFont(font);

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        agregarBordes(style);

        return style;
    }


    // ============================================================
    // CELDA CENTRADA
    // ============================================================

    public static CellStyle celdaCentrada(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) celda(workbook);

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        return style;
    }


    // ============================================================
    // FECHA
    // ============================================================

    public static CellStyle fecha(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) celda(workbook);

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setDataFormat(
                workbook.createDataFormat()
                        .getFormat("dd/MM/yyyy")
        );

        return style;
    }


    // ============================================================
    // ESTATUS ACTIVO
    // ============================================================

    public static CellStyle estatusActivo(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) celdaCentrada(workbook);

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setColor(
                new XSSFColor(COLOR_PRINCIPAL, null)
        );

        style.setFont(font);

        return style;
    }


    // ============================================================
    // ESTATUS BAJA
    // ============================================================

    public static CellStyle estatusBaja(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) celdaCentrada(workbook);

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 10);
        font.setBold(true);
        font.setColor(
                new XSSFColor(
                        new Color(190, 0, 0),
                        null
                )
        );

        style.setFont(font);

        return style;
    }


    // ============================================================
    // RESUMEN
    // ============================================================

    public static CellStyle resumenTitulo(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        font.setColor(
                new XSSFColor(COLOR_BLANCO, null)
        );

        style.setFont(font);

        style.setFillForegroundColor(
                new XSSFColor(COLOR_PRINCIPAL, null)
        );

        style.setFillPattern(
                FillPatternType.SOLID_FOREGROUND
        );

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        agregarBordes(style);

        return style;
    }


    // ============================================================
    // RESUMEN - VALOR
    // ============================================================

    public static CellStyle resumenValor(Workbook workbook) {

        XSSFCellStyle style =
                (XSSFCellStyle) workbook.createCellStyle();

        XSSFFont font = (XSSFFont) workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);
        font.setColor(
                new XSSFColor(COLOR_PRINCIPAL, null)
        );

        style.setFont(font);

        style.setFillForegroundColor(
                new XSSFColor(COLOR_FONDO_TITULO, null)
        );

        style.setFillPattern(
                FillPatternType.SOLID_FOREGROUND
        );

        style.setAlignment(
                HorizontalAlignment.CENTER
        );

        style.setVerticalAlignment(
                VerticalAlignment.CENTER
        );

        agregarBordes(style);

        return style;
    }


    // ============================================================
    // BORDES
    // ============================================================

    private static void agregarBordes(
            XSSFCellStyle style) {

        XSSFColor colorBorde =
                new XSSFColor(
                        COLOR_BORDE,
                        null
                );

        style.setBorderTop(
                BorderStyle.THIN
        );

        style.setBorderBottom(
                BorderStyle.THIN
        );

        style.setBorderLeft(
                BorderStyle.THIN
        );

        style.setBorderRight(
                BorderStyle.THIN
        );

        style.setTopBorderColor(colorBorde);
        style.setBottomBorderColor(colorBorde);
        style.setLeftBorderColor(colorBorde);
        style.setRightBorderColor(colorBorde);
    }
}