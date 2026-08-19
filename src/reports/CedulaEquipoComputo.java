package reports;

import modelo.Bien;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CedulaEquipoComputo {

    // ============================================================
    // GENERAR CÉDULA DE EQUIPO DE CÓMPUTO
    // ============================================================

    public static File generar(
            List<Bien> bienes,
            String nombreResguardante,
            String nombreArea,
            String rutaArchivo
    ) throws IOException {

        Workbook workbook =
                new XSSFWorkbook();

        try {

            Sheet sheet =
                    workbook.createSheet(
                            "Equipo de Cómputo"
                    );

            sheet.setDisplayGridlines(false);

            // ====================================================
            // ESTILOS
            // ====================================================

            CellStyle titulo =
                    Styles.titulo(workbook);

            CellStyle subtitulo =
                    Styles.subtitulo(workbook);

            CellStyle informacion =
                    Styles.informacion(workbook);

            CellStyle encabezado =
                    Styles.encabezado(workbook);

            CellStyle celda =
                    Styles.celda(workbook);

            CellStyle celdaCentrada =
                    Styles.celdaCentrada(workbook);

            CellStyle resumenValor =
                    Styles.resumenValor(workbook);

            // ====================================================
            // LOGO
            // ====================================================

            agregarLogo(
                    workbook,
                    sheet
            );

            // ====================================================
            // ENCABEZADO INSTITUCIONAL
            // ====================================================

            Row fila0 =
                    sheet.createRow(0);

            fila0.setHeightInPoints(
                    25
            );

            Cell tribunal =
                    fila0.createCell(0);

            tribunal.setCellValue(
                    "TRIBUNAL SUPERIOR AGRARIO"
            );

            tribunal.setCellStyle(
                    titulo
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            0,
                            0,
                            0,
                            6
                    )
            );

            // ----------------------------------------------------

            Row fila1 =
                    sheet.createRow(1);

            fila1.setHeightInPoints(
                    20
            );

            Cell distrito =
                    fila1.createCell(0);

            distrito.setCellValue(
                    "TRIBUNAL UNITARIO AGRARIO DISTRITO 49"
            );

            distrito.setCellStyle(
                    subtitulo
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            1,
                            1,
                            0,
                            6
                    )
            );

            // ====================================================
            // TÍTULO DEL REPORTE
            // ====================================================

            Row fila3 =
                    sheet.createRow(3);

            fila3.setHeightInPoints(
                    32
            );

            Cell tituloReporte =
                    fila3.createCell(0);

            tituloReporte.setCellValue(
                    "CÉDULA DE INVENTARIO FÍSICO "
                    + "DE EQUIPO DE CÓMPUTO"
            );

            tituloReporte.setCellStyle(
                    titulo
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            3,
                            3,
                            0,
                            6
                    )
            );

            // ====================================================
            // INFORMACIÓN GENERAL
            // ====================================================

            Row fila5 =
                    sheet.createRow(5);

            Cell ejercicio =
                    fila5.createCell(0);

            ejercicio.setCellValue(
                    "Ejercicio:"
            );

            ejercicio.setCellStyle(
                    informacion
            );

            Cell valorEjercicio =
                    fila5.createCell(1);

            valorEjercicio.setCellValue(
                    String.valueOf(
                            LocalDate.now().getYear()
                    )
            );

            valorEjercicio.setCellStyle(
                    informacion
            );

            Cell fechaTexto =
                    fila5.createCell(5);

            fechaTexto.setCellValue(
                    "Fecha de generación:"
            );

            fechaTexto.setCellStyle(
                    informacion
            );

            Cell fecha =
                    fila5.createCell(6);

            fecha.setCellValue(
                    LocalDate.now().format(
                            DateTimeFormatter.ofPattern(
                                    "dd/MM/yyyy"
                            )
                    )
            );

            fecha.setCellStyle(
                    informacion
            );

            // ====================================================
            // RESGUARDANTE
            // ====================================================

            Row fila7 =
                    sheet.createRow(7);

            Cell responsable =
                    fila7.createCell(0);

            responsable.setCellValue(
                    "RESPONSABLE DE RESGUARDO:"
            );

            responsable.setCellStyle(
                    encabezado
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            7,
                            7,
                            0,
                            2
                    )
            );

            Cell nombre =
                    fila7.createCell(3);

            nombre.setCellValue(
                    valor(nombreResguardante)
            );

            nombre.setCellStyle(
                    informacion
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            7,
                            7,
                            3,
                            6
                    )
            );

            // ====================================================
            // ÁREA
            // ====================================================

            Row fila8 =
                    sheet.createRow(8);

            Cell areaTexto =
                    fila8.createCell(0);

            areaTexto.setCellValue(
                    "ÁREA DE ADSCRIPCIÓN:"
            );

            areaTexto.setCellStyle(
                    encabezado
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            8,
                            8,
                            0,
                            2
                    )
            );

            Cell area =
                    fila8.createCell(3);

            area.setCellValue(
                    valor(nombreArea)
            );

            area.setCellStyle(
                    informacion
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            8,
                            8,
                            3,
                            6
                    )
            );

            // ====================================================
            // SECCIÓN HARDWARE
            // ====================================================

            int filaHardware =
                    10;

            Row hardware =
                    sheet.createRow(
                            filaHardware
                    );

            hardware.setHeightInPoints(
                    24
            );

            Cell hardwareTitulo =
                    hardware.createCell(0);

            hardwareTitulo.setCellValue(
                    "HARDWARE"
            );

            hardwareTitulo.setCellStyle(
                    titulo
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaHardware,
                            filaHardware,
                            0,
                            6
                    )
            );

            // ====================================================
            // ENCABEZADOS HARDWARE
            // ====================================================

            int filaEncabezado =
                    filaHardware + 1;

            Row header =
                    sheet.createRow(
                            filaEncabezado
                    );

            String[] columnas = {

                    "DESCRIPCIÓN",
                    "MARCA",
                    "MODELO",
                    "NÚMERO DE SERIE",
                    "NÚMERO DE INVENTARIO",
                    "FACTURA",
                    "PROVEEDOR"
            };

            for (
                    int i = 0;
                    i < columnas.length;
                    i++
            ) {

                Cell cell =
                        header.createCell(i);

                cell.setCellValue(
                        columnas[i]
                );

                cell.setCellStyle(
                        encabezado
                );
            }

            header.setHeightInPoints(
                    34
            );

            // ====================================================
            // DATOS HARDWARE
            // ====================================================

            int fila =
                    filaEncabezado + 1;

            int totalEquipos =
                    0;

            if (bienes != null) {

                for (Bien bien : bienes) {

                    if (bien == null) {
                        continue;
                    }

                    // --------------------------------------------
                    // SOLO ELECTRÓNICOS
                    // --------------------------------------------

                    if (
                            bien.getTipoBien() == null
                            ||
                            !"ELECTRONICO".equalsIgnoreCase(
                                    bien.getTipoBien()
                            )
                    ) {

                        continue;
                    }

                    if (
                            bien.getStatus() == null
                            ||
                            "BAJA".equalsIgnoreCase(
                                    bien.getStatus()
                            )
                    ) {
                        continue;
                    }

                    Row row =
                            sheet.createRow(
                                    fila++
                            );

                    // --------------------------------------------
                    // DESCRIPCIÓN
                    // --------------------------------------------

                    Cell descripcion =
                            row.createCell(0);

                    descripcion.setCellValue(
                            valor(
                                    bien.getDescripcion()
                            )
                    );

                    descripcion.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // MARCA
                    // --------------------------------------------

                    Cell marca =
                            row.createCell(1);

                    marca.setCellValue(
                            valor(
                                    bien.getMarca()
                            )
                    );

                    marca.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // MODELO
                    // --------------------------------------------

                    Cell modelo =
                            row.createCell(2);

                    modelo.setCellValue(
                            valor(
                                    bien.getModelo()
                            )
                    );

                    modelo.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // SERIE
                    // --------------------------------------------

                    Cell serie =
                            row.createCell(3);

                    serie.setCellValue(
                            valor(
                                    bien.getNumeroSerie()
                            )
                    );

                    serie.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // INVENTARIO
                    // --------------------------------------------

                    Cell inventario =
                            row.createCell(4);

                    inventario.setCellValue(
                            valor(
                                    bien.getNumeroInventario()
                            )
                    );

                    inventario.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // FACTURA
                    // --------------------------------------------

                    Cell factura =
                            row.createCell(5);

                    factura.setCellValue(
                            valor(
                                    bien.getFactura()
                            )
                    );

                    factura.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // PROVEEDOR
                    // --------------------------------------------

                    Cell proveedor =
                            row.createCell(6);

                    proveedor.setCellValue(
                            valor(
                                    bien.getProveedor()
                            )
                    );

                    proveedor.setCellStyle(
                            celda
                    );

                    totalEquipos++;
                }
            }

            // ====================================================
            // TOTAL HARDWARE
            // ====================================================

            Row filaTotal =
                    sheet.createRow(
                            fila + 1
                    );

            Cell totalTexto =
                    filaTotal.createCell(0);

            totalTexto.setCellValue(
                    "TOTAL DE EQUIPO ELECTRÓNICO:"
            );

            totalTexto.setCellStyle(
                    encabezado
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            fila + 1,
                            fila + 1,
                            0,
                            4
                    )
            );

            Cell total =
                    filaTotal.createCell(5);

            total.setCellValue(
                    totalEquipos
            );

            total.setCellStyle(
                    resumenValor
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            fila + 1,
                            fila + 1,
                            5,
                            6
                    )
            );

            // ====================================================
            // SOFTWARE
            // ====================================================

            int filaSoftware =
                    fila + 3;

            Row software =
                    sheet.createRow(
                            filaSoftware
                    );

            software.setHeightInPoints(
                    24
            );

            Cell softwareTitulo =
                    software.createCell(0);

            softwareTitulo.setCellValue(
                    "SOFTWARE"
            );

            softwareTitulo.setCellStyle(
                    titulo
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaSoftware,
                            filaSoftware,
                            0,
                            6
                    )
            );

            // ====================================================
            // ENCABEZADOS SOFTWARE
            // ====================================================

            Row softwareHeader =
                    sheet.createRow(
                            filaSoftware + 1
                    );

            String[] columnasSoftware = {

                    "SOFTWARE",
                    "VERSIÓN",
                    "LICENCIA",
                    "OBSERVACIONES"
            };

            for (
                    int i = 0;
                    i < columnasSoftware.length;
                    i++
            ) {

                Cell cell =
                        softwareHeader.createCell(i);

                cell.setCellValue(
                        columnasSoftware[i]
                );

                cell.setCellStyle(
                        encabezado
                );
            }

            /*
             * Actualmente tu clase Bien no tiene información
             * de software.
             *
             * Por eso dejamos una fila vacía para que la
             * cédula conserve la sección.
             *
             * Cuando tengamos una tabla de software en la BD,
             * esta parte se puede llenar automáticamente.
             */

            Row softwareVacio =
                    sheet.createRow(
                            filaSoftware + 2
                    );

            softwareVacio.setHeightInPoints(
                    28
            );

            for (
                    int i = 0;
                    i < 4;
                    i++
            ) {

                Cell cell =
                        softwareVacio.createCell(i);

                cell.setCellValue(
                        ""
                );

                cell.setCellStyle(
                        celda
                );
            }

            // ====================================================
            // DECLARACIÓN
            // ====================================================

            int filaDeclaracion =
                    filaSoftware + 5;

            Row declaracionTitulo =
                    sheet.createRow(
                            filaDeclaracion
                    );

            Cell declaracion =
                    declaracionTitulo.createCell(0);

            declaracion.setCellValue(
                    "DECLARACIÓN"
            );

            declaracion.setCellStyle(
                    encabezado
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaDeclaracion,
                            filaDeclaracion,
                            0,
                            6
                    )
            );

            Row textoDeclaracion =
                    sheet.createRow(
                            filaDeclaracion + 1
                    );

            Cell texto =
                    textoDeclaracion.createCell(0);

            texto.setCellValue(
                    "Declaro que los equipos relacionados en la "
                    + "presente cédula se encuentran bajo mi "
                    + "responsabilidad y resguardo, comprometiéndome"
            );

            texto.setCellStyle(
                    informacion
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaDeclaracion + 1,
                            filaDeclaracion + 1,
                            0,
                            6
                    )
            );

            Row textoDeclaracion1 =
                    sheet.createRow(
                            filaDeclaracion + 2
                    );

            Cell texto1 =
                    textoDeclaracion1.createCell(0);

            texto1.setCellValue(
                    "a informar cualquier cambio, daño, pérdida "
                    + "o situación que afecte su estado, ubicación "
                    + "o funcionamiento."
            );

            texto1.setCellStyle(
                    informacion
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaDeclaracion + 2,
                            filaDeclaracion + 2,
                            0,
                            6
                    )
            );

            // ====================================================
            // FIRMAS
            // ====================================================

            int filaFirma =
                    filaDeclaracion + 5;

            Row firma =
                    sheet.createRow(
                            filaFirma
                    );

            Cell firmaResguardante =
                    firma.createCell(1);

            firmaResguardante.setCellValue(
                    "________________________________"
            );

            firmaResguardante.setCellStyle(
                    celdaCentrada
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaFirma,
                            filaFirma,
                            1,
                            2
                    )
            );

            Cell firmaVoBo =
                    firma.createCell(4);

            firmaVoBo.setCellValue(
                    "________________________________"
            );

            firmaVoBo.setCellStyle(
                    celdaCentrada
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaFirma,
                            filaFirma,
                            4,
                            5
                    )
            );

            // ----------------------------------------------------

            Row nombresFirma =
                    sheet.createRow(
                            filaFirma + 1
                    );

            Cell nombreFirma =
                    nombresFirma.createCell(1);

            nombreFirma.setCellValue(
                    valor(
                            nombreResguardante
                    )
            );

            nombreFirma.setCellStyle(
                    celdaCentrada
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaFirma + 1,
                            filaFirma + 1,
                            1,
                            2
                    )
            );

            Cell nombreVoBo =
                    nombresFirma.createCell(4);

            nombreVoBo.setCellValue(
                    "Vo. Bo."
            );

            nombreVoBo.setCellStyle(
                    celdaCentrada
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaFirma + 1,
                            filaFirma + 1,
                            4,
                            5
                    )
            );

            // ====================================================
            // CONFIGURACIÓN DE IMPRESIÓN
            // ====================================================

            sheet.setAutobreaks(
                    true
            );

            sheet.getPrintSetup()
                    .setLandscape(true);

            sheet.getPrintSetup()
                    .setFitHeight(
                            (short) 0
                    );

            sheet.setFitToPage(
                    true
            );

            // ====================================================
            // REPETIR ENCABEZADOS
            // ====================================================

            sheet.setRepeatingRows(
                    new CellRangeAddress(
                            filaEncabezado,
                            filaEncabezado,
                            0,
                            6
                    )
            );

            for (int i = 0; i < sheet.getRow(11).getLastCellNum(); i++) {

                sheet.autoSizeColumn(i);

            }

            // ====================================================
            // GUARDAR ARCHIVO
            // ====================================================

            File archivo =
                    new File(
                            rutaArchivo
                    );

            File directorio =
                    archivo.getParentFile();

            if (
                    directorio != null
                    &&
                    !directorio.exists()
            ) {

                directorio.mkdirs();
            }

            try (
                    FileOutputStream output =
                            new FileOutputStream(
                                    archivo
                            )
            ) {

                workbook.write(
                        output
                );
            }

            return archivo;

        } finally {

            workbook.close();
        }
    }

    // ============================================================
    // AGREGAR LOGO
    // ============================================================

    private static void agregarLogo(
            Workbook workbook,
            Sheet sheet
    ) {

        String rutaLogo =
                "src/images/tua49v.png";

        File archivoLogo =
                new File(
                        rutaLogo
                );

        if (
                !archivoLogo.exists()
        ) {

            return;
        }

        try {

            byte[] bytes;

            try (
                    FileInputStream input =
                            new FileInputStream(
                                    archivoLogo
                            )
            ) {

                bytes =
                        IOUtils.toByteArray(
                                input
                        );
            }

            int pictureIndex =
                    workbook.addPicture(
                            bytes,
                            Workbook.PICTURE_TYPE_PNG
                    );

            Drawing<?> drawing =
                    sheet.createDrawingPatriarch();

            XSSFClientAnchor anchor = new XSSFClientAnchor();

            anchor.setCol1(0);
            anchor.setRow1(0);

            // Posición final aproximada dentro de la primera celda
            anchor.setCol2(0);
            anchor.setRow2(3);

            // 3 cm × 3 cm aproximadamente
            anchor.setDx2(1085605);
            anchor.setDy2(1085605);

            drawing.createPicture(
                    anchor,
                    pictureIndex
            );

        } catch (Exception e) {

            System.err.println(
                    "No fue posible cargar el logo: "
                    + e.getMessage()
            );
        }
    }

    // ============================================================
    // VALOR SEGURO
    // ============================================================

    private static String valor(
            String valor
    ) {

        return valor == null
                ? ""
                : valor;
    }
}