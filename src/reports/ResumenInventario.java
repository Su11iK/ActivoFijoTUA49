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
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
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

            CellStyle celdaCentrada =
                    Styles.celdaCentrada(workbook);

            CellStyle resumenValor =
                    Styles.resumenValor(workbook);

            // ====================================================
            // ANCHOS
            // ====================================================

            sheet.setColumnWidth(
                    0,
                    7 * 256
            );

            sheet.setColumnWidth(
                    1,
                    18 * 256
            );

            sheet.setColumnWidth(
                    2,
                    32 * 256
            );

            sheet.setColumnWidth(
                    3,
                    18 * 256
            );

            sheet.setColumnWidth(
                    4,
                    20 * 256
            );

            sheet.setColumnWidth(
                    5,
                    24 * 256
            );

            sheet.setColumnWidth(
                    6,
                    20 * 256
            );

            sheet.setColumnWidth(
                    7,
                    18 * 256
            );

            sheet.setColumnWidth(
                    8,
                    28 * 256
            );

            sheet.setColumnWidth(
                    9,
                    25 * 256
            );

            sheet.setColumnWidth(
                    10,
                    28 * 256
            );

            sheet.setColumnWidth(
                    11,
                    16 * 256
            );

            sheet.setColumnWidth(
                    12,
                    16 * 256
            );

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
                    fila0.createCell(2);

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
                            2,
                            12
                    )
            );

            // ----------------------------------------------------

            Row fila1 =
                    sheet.createRow(1);

            fila1.setHeightInPoints(
                    20
            );

            Cell distrito =
                    fila1.createCell(2);

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
                            2,
                            12
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
                            12
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
                    fila5.createCell(8);

            fechaTexto.setCellValue(
                    "Fecha de generación:"
            );

            fechaTexto.setCellStyle(
                    informacion
            );

            Cell fecha =
                    fila5.createCell(9);

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

            sheet.addMergedRegion(
                    new CellRangeAddress(
                            5,
                            5,
                            9,
                            10
                    )
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
                            12
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
                            12
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
                            12
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

                    "No.",
                    "INVENTARIO",
                    "DESCRIPCIÓN",
                    "MARCA",
                    "MODELO",
                    "NÚMERO DE SERIE",
                    "ESTADO FÍSICO",
                    "FACTURA",
                    "PROVEEDOR",
                    "TIPO DE BIEN",
                    "ÁREA",
                    "RESGUARDANTE",
                    "ESTATUS"
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

            int contador =
                    1;

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
                    // No.
                    // --------------------------------------------

                    crearCelda(
                            row,
                            0,
                            contador++,
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // INVENTARIO
                    // --------------------------------------------

                    crearCelda(
                            row,
                            1,
                            valor(
                                    bien.getNumeroInventario()
                            ),
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // DESCRIPCIÓN
                    // --------------------------------------------

                    crearCelda(
                            row,
                            2,
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
                            3,
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
                            4,
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
                            5,
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
                            6,
                            valor(
                                    bien.getEstadoFisico()
                            ),
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // FACTURA
                    // --------------------------------------------

                    crearCelda(
                            row,
                            7,
                            valor(
                                    bien.getFactura()
                            ),
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // PROVEEDOR
                    // --------------------------------------------

                    crearCelda(
                            row,
                            8,
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
                            9,
                            valor(
                                    bien.getTipoBien()
                            ),
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // ÁREA
                    // --------------------------------------------

                    crearCelda(
                            row,
                            10,
                            valor(
                                    bien.getArea()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // RESGUARDANTE
                    // --------------------------------------------

                    crearCelda(
                            row,
                            11,
                            valor(
                                    bien.getResguardante()
                            ),
                            celda
                    );

                    // --------------------------------------------
                    // ESTATUS
                    // --------------------------------------------

                    crearCelda(
                            row,
                            12,
                            valor(
                                    bien.getStatus()
                            ),
                            celdaCentrada
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
                    .setFitWidth(
                            (short) 1
                    );

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
                            12
                    )
            );

            // ====================================================
            // MÁRGENES
            // ====================================================

            sheet.setMargin(
                    Sheet.LeftMargin,
                    0.30
            );

            sheet.setMargin(
                    Sheet.RightMargin,
                    0.30
            );

            sheet.setMargin(
                    Sheet.TopMargin,
                    0.50
            );

            sheet.setMargin(
                    Sheet.BottomMargin,
                    0.50
            );

            // ====================================================
            // CONGELAR ENCABEZADOS
            // ====================================================

            sheet.createFreezePane(
                    0,
                    filaDetalle + 2
            );

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
                "src/resources/tua49.png";

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

            CreationHelper helper =
                    workbook.getCreationHelper();

            Drawing<?> drawing =
                    sheet.createDrawingPatriarch();

            ClientAnchor anchor =
                    helper.createClientAnchor();

            anchor.setCol1(0);
            anchor.setRow1(0);
            anchor.setCol2(2);
            anchor.setRow2(2);

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