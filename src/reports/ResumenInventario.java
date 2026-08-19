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

public class ResumenInventario {

    // ============================================================
    // GENERAR RESUMEN GENERAL DE INVENTARIO
    // ============================================================

    public static File generar(
            List<Bien> bienes,
            String rutaArchivo
    ) throws IOException {

        Workbook workbook =
                new XSSFWorkbook();

        try {

            Sheet sheet =
                    workbook.createSheet(
                            "Resumen de Inventario"
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
                            8
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
                            8
                    )
            );

            // ====================================================
            // TÍTULO
            // ====================================================

            Row fila3 =
                    sheet.createRow(3);

            fila3.setHeightInPoints(
                    30
            );

            Cell tituloReporte =
                    fila3.createCell(0);

            tituloReporte.setCellValue(
                    "RESUMEN GENERAL DE INVENTARIO"
            );

            tituloReporte.setCellStyle(
                    titulo
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            3,
                            3,
                            0,
                            8
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
                    fila5.createCell(7);

            fechaTexto.setCellValue(
                    "Fecha de generación:"
            );

            fechaTexto.setCellStyle(
                    informacion
            );

            Cell fecha =
                    fila5.createCell(8);

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
            // CALCULAR ESTADÍSTICAS
            // ====================================================

            int totalBienes = 0;

            int totalMuebles = 0;

            int totalElectronicos = 0;

            if (bienes != null) {

                for (Bien bien : bienes) {

                    if (bien == null) {
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

                    totalBienes++;

                    String tipo =
                            valor(
                                    bien.getTipoBien()
                            );

                    boolean esMueble =
                            "MUEBLE".equalsIgnoreCase(
                                    tipo
                            );

                    boolean esElectronico =
                            "ELECTRONICO".equalsIgnoreCase(
                                    tipo
                            );

                    if (esMueble) {

                        totalMuebles++;
                    }

                    if (esElectronico) {

                        totalElectronicos++;
                    }
                }
            }

            // ====================================================
            // RESUMEN GENERAL
            // ====================================================

            int filaResumen =
                    7;

            Row tituloResumen =
                    sheet.createRow(
                            filaResumen
                    );

            Cell resumen =
                    tituloResumen.createCell(0);

            resumen.setCellValue(
                    "RESUMEN"
            );

            resumen.setCellStyle(
                    encabezado
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaResumen,
                            filaResumen,
                            0,
                            8
                    )
            );

            // ----------------------------------------------------
            // ENCABEZADOS RESUMEN
            // ----------------------------------------------------

            Row headerResumen =
                    sheet.createRow(
                            filaResumen + 1
                    );

            String[] columnasResumen = {

                    "CONCEPTO",
                    "TOTAL"
            };

            for (
                    int i = 0;
                    i < columnasResumen.length;
                    i++
            ) {

                Cell cell =
                        headerResumen.createCell(i);

                cell.setCellValue(
                        columnasResumen[i]
                );

                cell.setCellStyle(
                        encabezado
                );
            }

            // ----------------------------------------------------
            // FILA TOTAL GENERAL
            // ----------------------------------------------------

            Row rowTotal =
                    sheet.createRow(
                            filaResumen + 2
                    );

            crearCelda(
                    rowTotal,
                    0,
                    "TOTAL DE BIENES",
                    celda
            );

            crearCelda(
                    rowTotal,
                    1,
                    totalBienes,
                    resumenValor
            );

            // ----------------------------------------------------
            // MUEBLES
            // ----------------------------------------------------

            Row rowMuebles =
                    sheet.createRow(
                            filaResumen + 3
                    );

            crearCelda(
                    rowMuebles,
                    0,
                    "MUEBLES",
                    celda
            );

            crearCelda(
                    rowMuebles,
                    1,
                    totalMuebles,
                    resumenValor
            );

            // ----------------------------------------------------
            // ELECTRÓNICOS
            // ----------------------------------------------------

            Row rowElectronicos =
                    sheet.createRow(
                            filaResumen + 4
                    );

            crearCelda(
                    rowElectronicos,
                    0,
                    "ELECTRÓNICOS",
                    celda
            );

            crearCelda(
                    rowElectronicos,
                    1,
                    totalElectronicos,
                    resumenValor
            );

            // ====================================================
            // INVENTARIO POR TIPO
            // ====================================================

            int filaTipo =
                    filaResumen + 7;

            Row tituloTipo =
                    sheet.createRow(
                            filaTipo
                    );

            Cell tipoTitulo =
                    tituloTipo.createCell(0);

            tipoTitulo.setCellValue(
                    "DISTRIBUCIÓN POR TIPO DE BIEN"
            );

            tipoTitulo.setCellStyle(
                    encabezado
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaTipo,
                            filaTipo,
                            0,
                            8
                    )
            );

            Row headerTipo =
                    sheet.createRow(
                            filaTipo + 1
                    );

            crearCelda(
                    headerTipo,
                    0,
                    "TIPO DE BIEN",
                    encabezado
            );

            crearCelda(
                    headerTipo,
                    1,
                    "CANTIDAD",
                    encabezado
            );

            crearCelda(
                    headerTipo,
                    2,
                    "PORCENTAJE",
                    encabezado
            );

            // ----------------------------------------------------
            // MUEBLES
            // ----------------------------------------------------

            Row tipoMueble =
                    sheet.createRow(
                            filaTipo + 2
                    );

            crearCelda(
                    tipoMueble,
                    0,
                    "MUEBLE",
                    celda
            );

            crearCelda(
                    tipoMueble,
                    1,
                    totalMuebles,
                    resumenValor
            );

            double porcentajeMueble =
                    totalBienes == 0
                            ? 0
                            : (
                                (double) totalMuebles
                                / totalBienes
                            ) * 100;

            Cell porcentajeMuebleCell =
                    tipoMueble.createCell(2);

            porcentajeMuebleCell.setCellValue(
                    porcentajeMueble / 100
            );

            porcentajeMuebleCell.setCellStyle(
                    Styles.porcentaje(
                            workbook
                    )
            );

            // ----------------------------------------------------
            // ELECTRÓNICOS
            // ----------------------------------------------------

            Row tipoElectronico =
                    sheet.createRow(
                            filaTipo + 3
                    );

            crearCelda(
                    tipoElectronico,
                    0,
                    "ELECTRONICO",
                    celda
            );

            crearCelda(
                    tipoElectronico,
                    1,
                    totalElectronicos,
                    resumenValor
            );

            double porcentajeElectronico =
                    totalBienes == 0
                            ? 0
                            : (
                                (double) totalElectronicos
                                / totalBienes
                            ) * 100;

            Cell porcentajeElectronicoCell =
                    tipoElectronico.createCell(2);

            porcentajeElectronicoCell.setCellValue(
                    porcentajeElectronico / 100
            );

            porcentajeElectronicoCell.setCellStyle(
                    Styles.porcentaje(
                            workbook
                    )
            );

            // ====================================================
            // DETALLE DEL INVENTARIO
            // ====================================================

            int filaDetalle =
                    filaTipo + 6;

            Row tituloDetalle =
                    sheet.createRow(
                            filaDetalle
                    );

            Cell detalle =
                    tituloDetalle.createCell(0);

            detalle.setCellValue(
                    "DETALLE DEL INVENTARIO"
            );

            detalle.setCellStyle(
                    encabezado
            );

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            filaDetalle,
                            filaDetalle,
                            0,
                            8
                    )
            );

            // ====================================================
            // ENCABEZADOS DETALLE
            // ====================================================

            Row header =
                    sheet.createRow(
                            filaDetalle + 1
                    );

            String[] columnas = {

                    "INVENTARIO",
                    "DESCRIPCIÓN",
                    "MARCA",
                    "MODELO",
                    "NÚMERO DE SERIE",
                    "ESTADO FÍSICO",
                    "FACTURA",
                    "PROVEEDOR",
                    "TIPO DE BIEN"
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
                    35
            );

            // ====================================================
            // DATOS DEL INVENTARIO
            // ====================================================

            int fila =
                    filaDetalle + 2;

            if (bienes != null) {

                for (Bien bien : bienes) {

                    if (bien == null) {
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
                    // INVENTARIO
                    // --------------------------------------------

                    crearCelda(
                            row,
                            0,
                            valor(
                                    bien.getNumeroInventario()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // DESCRIPCIÓN
                    // --------------------------------------------

                    crearCelda(
                            row,
                            1,
                            valor(
                                    bien.getDescripcion()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // MARCA
                    // --------------------------------------------

                    crearCelda(
                            row,
                            2,
                            valor(
                                    bien.getMarca()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // MODELO
                    // --------------------------------------------

                    crearCelda(
                            row,
                            3,
                            valor(
                                    bien.getModelo()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // SERIE
                    // --------------------------------------------

                    crearCelda(
                            row,
                            4,
                            valor(
                                    bien.getNumeroSerie()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // ESTADO FÍSICO
                    // --------------------------------------------

                    crearCelda(
                            row,
                            5,
                            valor(
                                    bien.getEstadoFisico()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // FACTURA
                    // --------------------------------------------

                    crearCelda(
                            row,
                            6,
                            valor(
                                    bien.getFactura()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // PROVEEDOR
                    // --------------------------------------------

                    crearCelda(
                            row,
                            7,
                            valor(
                                    bien.getProveedor()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // TIPO
                    // --------------------------------------------

                    crearCelda(
                            row,
                            8,
                            valor(
                                    bien.getTipoBien()
                            ),
                            celda
                    );
                }
            }

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
            // REPETIR ENCABEZADO DEL DETALLE
            // ====================================================

            sheet.setRepeatingRows(
                    new CellRangeAddress(
                            filaDetalle + 1,
                            filaDetalle + 1,
                            0,
                            9
                    )
            );

            for (int i = 0; i < sheet.getRow(21).getLastCellNum(); i++) {

                sheet.autoSizeColumn(i);

            }

            // ====================================================
            // GUARDAR
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
    // CREAR CELDA - TEXTO
    // ============================================================

    private static void crearCelda(
            Row row,
            int columna,
            String valor,
            CellStyle estilo
    ) {

        Cell cell =
                row.createCell(
                        columna
                );

        cell.setCellValue(
                valor
        );

        cell.setCellStyle(
                estilo
        );
    }

    // ============================================================
    // CREAR CELDA - NÚMERO
    // ============================================================

    private static void crearCelda(
            Row row,
            int columna,
            int valor,
            CellStyle estilo
    ) {

        Cell cell =
                row.createCell(
                        columna
                );

        cell.setCellValue(
                valor
        );

        cell.setCellStyle(
                estilo
        );
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