package reports;

import modelo.Bien;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportService {

    // ============================================================
    // GENERAR REPORTE GENERAL
    // ============================================================

    public static File generarReporteGeneral(
            List<Bien> bienes,
            String rutaArchivo
    ) throws IOException {

        Workbook workbook = new XSSFWorkbook();

        try {

            Sheet sheet =
                    workbook.createSheet("Inventario");

            // ====================================================
            // CONFIGURACIÓN
            // ====================================================

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

            // ====================================================
            // TÍTULO
            // ====================================================

            Row filaTitulo =
                    sheet.createRow(0);

            filaTitulo.setHeightInPoints(28);

            Cell celdaTitulo =
                    filaTitulo.createCell(0);

            celdaTitulo.setCellValue(
                    "TRIBUNAL UNITARIO AGRARIO DISTRITO 49"
            );

            celdaTitulo.setCellStyle(titulo);

            sheet.addMergedRegion(
                    new org.apache.poi.ss.util.CellRangeAddress(
                            0,
                            0,
                            0,
                            12
                    )
            );

            // ====================================================
            // SUBTÍTULO
            // ====================================================

            Row filaSubtitulo =
                    sheet.createRow(1);

            filaSubtitulo.setHeightInPoints(22);

            Cell celdaSubtitulo =
                    filaSubtitulo.createCell(0);

            celdaSubtitulo.setCellValue(
                    "SISTEMA DE INVENTARIO DE ACTIVOS FIJOS"
            );

            celdaSubtitulo.setCellStyle(subtitulo);

            sheet.addMergedRegion(
                    new org.apache.poi.ss.util.CellRangeAddress(
                            1,
                            1,
                            0,
                            12
                    )
            );

            // ====================================================
            // INFORMACIÓN DEL REPORTE
            // ====================================================

            Row filaFecha =
                    sheet.createRow(3);

            Cell celdaFechaTexto =
                    filaFecha.createCell(0);

            celdaFechaTexto.setCellValue(
                    "Fecha de generación:"
            );

            celdaFechaTexto.setCellStyle(informacion);

            Cell celdaFecha =
                    filaFecha.createCell(1);

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern(
                            "dd/MM/yyyy HH:mm"
                    );

            celdaFecha.setCellValue(
                    LocalDateTime.now().format(formatter)
            );

            celdaFecha.setCellStyle(informacion);

            // ====================================================
            // TOTAL DE BIENES
            // ====================================================

            Row filaTotal =
                    sheet.createRow(4);

            Cell celdaTotalTexto =
                    filaTotal.createCell(0);

            celdaTotalTexto.setCellValue(
                    "Total de bienes:"
            );

            celdaTotalTexto.setCellStyle(informacion);

            Cell celdaTotal =
                    filaTotal.createCell(1);

            celdaTotal.setCellValue(
                    bienes != null
                            ? bienes.size()
                            : 0
            );

            celdaTotal.setCellStyle(
                    celdaCentrada
            );

            // ====================================================
            // ENCABEZADOS
            // ====================================================

            String[] columnas = {

                    "No. Inventario",
                    "Descripción",
                    "Marca",
                    "Modelo",
                    "Serie",
                    "Estado Físico",
                    "Factura",
                    "Proveedor",
                    "Tipo Bien",
                    "Área",
                    "Resguardante",
                    "Fecha Alta",
                    "Estatus"
            };

            int filaEncabezado = 6;

            Row rowHeader =
                    sheet.createRow(filaEncabezado);

            rowHeader.setHeightInPoints(30);

            for (int i = 0;
                 i < columnas.length;
                 i++) {

                Cell cell =
                        rowHeader.createCell(i);

                cell.setCellValue(
                        columnas[i]
                );

                cell.setCellStyle(
                        encabezado
                );
            }

            // ====================================================
            // DATOS
            // ====================================================

            int filaActual =
                    filaEncabezado + 1;

            if (bienes != null) {

                for (Bien bien : bienes) {

                    Row row =
                            sheet.createRow(
                                    filaActual++
                            );

                    // --------------------------------------------
                    // INVENTARIO
                    // --------------------------------------------

                    Cell inventario =
                            row.createCell(0);

                    inventario.setCellValue(
                            valor(
                                    bien.getNumeroInventario()
                            )
                    );

                    inventario.setCellStyle(
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // DESCRIPCIÓN
                    // --------------------------------------------

                    Cell descripcion =
                            row.createCell(1);

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
                            row.createCell(2);

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
                            row.createCell(3);

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
                            row.createCell(4);

                    serie.setCellValue(
                            valor(
                                    bien.getNumeroSerie()
                            )
                    );

                    serie.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // ESTADO FÍSICO
                    // --------------------------------------------

                    Cell estado =
                            row.createCell(5);

                    estado.setCellValue(
                            valor(
                                    bien.getEstadoFisico()
                            )
                    );

                    estado.setCellStyle(
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // FACTURA
                    // --------------------------------------------

                    Cell factura =
                            row.createCell(6);

                    factura.setCellValue(
                            valor(
                                    bien.getFactura()
                            )
                    );

                    factura.setCellStyle(
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // PROVEEDOR
                    // --------------------------------------------

                    Cell proveedor =
                            row.createCell(7);

                    proveedor.setCellValue(
                            valor(
                                    bien.getProveedor()
                            )
                    );

                    proveedor.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // TIPO DE BIEN
                    // --------------------------------------------

                    Cell tipoBien =
                            row.createCell(8);

                    tipoBien.setCellValue(
                            valor(
                                    bien.getTipoBien()
                            )
                    );

                    tipoBien.setCellStyle(
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // ÁREA
                    // --------------------------------------------

                    Cell area =
                            row.createCell(9);

                    area.setCellValue(
                            obtenerArea(bien)
                    );

                    area.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // RESGUARDANTE
                    // --------------------------------------------

                    Cell resguardante =
                            row.createCell(10);

                    resguardante.setCellValue(
                            obtenerResguardante(bien)
                    );

                    resguardante.setCellStyle(
                            celda
                    );

                    // --------------------------------------------
                    // FECHA DE ALTA
                    // --------------------------------------------

                    Cell fecha =
                            row.createCell(11);

                    fecha.setCellValue(
                            obtenerFechaAlta(bien)
                    );

                    fecha.setCellStyle(
                            celdaCentrada
                    );

                    // --------------------------------------------
                    // ESTATUS
                    // --------------------------------------------

                    Cell estatus =
                            row.createCell(12);

                    estatus.setCellValue(
                            valor(
                                    bien.getStatus()
                            )
                    );

                    estatus.setCellStyle(
                            celdaCentrada
                    );
                }
            }

            // ====================================================
            // FILTRO AUTOMÁTICO
            // ====================================================

            if (filaActual > filaEncabezado + 1) {

                sheet.setAutoFilter(
                        new org.apache.poi.ss.util.CellRangeAddress(
                                filaEncabezado,
                                filaActual - 1,
                                0,
                                columnas.length - 1
                        )
                );
            }

            // ====================================================
            // CONGELAR ENCABEZADO
            // ====================================================

            sheet.createFreezePane(
                    0,
                    filaEncabezado + 1
            );

            // ====================================================
            // ANCHOS
            // ====================================================

            int[] anchos = {

                    16,
                    35,
                    18,
                    20,
                    25,
                    16,
                    16,
                    30,
                    16,
                    28,
                    32,
                    18,
                    16
            };

            for (int i = 0;
                 i < anchos.length;
                 i++) {

                sheet.setColumnWidth(
                        i,
                        anchos[i] * 256
                );
            }

            // ====================================================
            // GUARDAR ARCHIVO
            // ====================================================

            File archivo =
                    new File(rutaArchivo);

            File directorio =
                    archivo.getParentFile();

            if (directorio != null) {

                directorio.mkdirs();
            }

            try (
                    FileOutputStream output =
                            new FileOutputStream(archivo)
            ) {

                workbook.write(output);
            }

            return archivo;

        } finally {

            workbook.close();
        }
    }


    // ============================================================
    // MANEJO DE VALORES NULL
    // ============================================================

    private static String valor(
            String valor
    ) {

        if (valor == null) {

            return "";
        }

        return valor;
    }


    // ============================================================
    // ÁREA
    // ============================================================

    private static String obtenerArea(
            Bien bien
    ) {

        /*
         * Esta parte la ajustaremos cuando confirmemos
         * exactamente cómo está representada el área dentro
         * de la clase Bien.
         */

        try {

            Object area =
                    bien.getArea();

            return area == null
                    ? ""
                    : area.toString();

        } catch (Exception e) {

            return "";
        }
    }


    // ============================================================
    // RESGUARDANTE
    // ============================================================

    private static String obtenerResguardante(
            Bien bien
    ) {

        /*
         * Igual que el área, aquí utilizamos el objeto que
         * actualmente tenga Bien.
         */

        try {

            Object resguardante =
                    bien.getResguardante();

            return resguardante == null
                    ? ""
                    : resguardante.toString();

        } catch (Exception e) {

            return "";
        }
    }


    // ============================================================
    // FECHA DE ALTA
    // ============================================================

    private static String obtenerFechaAlta(
            Bien bien
    ) {

        try {

            Object fecha =
                    bien.getFechaAlta();

            return fecha == null
                    ? ""
                    : fecha.toString();

        } catch (Exception e) {

            return "";
        }
    }
}