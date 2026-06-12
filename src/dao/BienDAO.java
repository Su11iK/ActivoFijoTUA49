package dao;

import conexion.ConexionBD;
import modelo.Bien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BienDAO {

    public List<Bien> listarBienes() {
        List<Bien> lista = new ArrayList<>();

        String sql = """
            SELECT 
                b.id_bien,
                b.numero_inventario,
                tipo_adquisicion,
                b.descripcion,
                b.marca,
                b.modelo,
                b.numero_serie,
                b.estado_fisico,
                b.numero_factura,
                b.proveedor,
                b.tipo_bien,
                b.fecha_alta,
                b.status,
                a.nombre_area,
                r.nombre_resguardante
            FROM bienes b
            LEFT JOIN areas a ON b.area_id = a.id_area
            LEFT JOIN resguardantes r ON b.resguardante_id = r.id_resguardante
            WHERE b.status <> 'BAJA'
            """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Bien b = new Bien();

                b.setId(rs.getInt("id_bien"));
                b.setNumeroInventario(rs.getString("numero_inventario"));
                b.setTipoAdquisicion(rs.getString("tipo_adquisicion"));
                b.setDescripcion(rs.getString("descripcion"));
                b.setMarca(rs.getString("marca"));
                b.setModelo(rs.getString("modelo"));
                b.setNumeroSerie(rs.getString("numero_serie"));

                // 🔥 NUEVOS CAMPOS
                b.setEstadoFisico(rs.getString("estado_fisico"));
                b.setFactura(rs.getString("numero_factura"));
                b.setProveedor(rs.getString("proveedor"));
                b.setTipoBien(rs.getString("tipo_bien"));

                b.setArea(rs.getString("nombre_area"));
                b.setResguardante(rs.getString("nombre_resguardante"));

                b.setFechaAlta(rs.getTimestamp("fecha_alta").toLocalDateTime());
                b.setStatus(rs.getString("status"));

                lista.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int insertarBien(Bien b) {

        String sql = """
            INSERT INTO bienes (
                tipo_adquisicion,
                numero_inventario,
                descripcion,
                marca,
                modelo,
                numero_serie,
                estado_fisico,
                numero_factura,
                proveedor,
                tipo_bien,
                fecha_alta,
                status
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 'ACTIVO')
            RETURNING id_bien
        """;

        try (Connection conn = ConexionBD.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getTipoAdquisicion());
            ps.setString(2, b.getNumeroInventario());
            ps.setString(3, b.getDescripcion());
            ps.setString(4, b.getMarca());
            ps.setString(5, b.getModelo());
            ps.setString(6, b.getNumeroSerie());
            ps.setString(7, b.getEstadoFisico());
            ps.setString(8, b.getFactura());
            ps.setString(9, b.getProveedor());
            ps.setString(10, b.getTipoBien());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_bien");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean actualizarBien(Bien b) {

        String sql = """
            UPDATE bienes
            SET
                descripcion = ?,
                marca = ?,
                modelo = ?,
                numero_serie = ?,
                estado_fisico = ?,
                numero_factura = ?,
                proveedor = ?,
                tipo_bien = ?,
                status = ?
            WHERE id_bien = ?
        """;

        try (Connection conn = ConexionBD.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getDescripcion());
            ps.setString(2, b.getMarca());
            ps.setString(3, b.getModelo());
            ps.setString(4, b.getNumeroSerie());
            ps.setString(5, b.getEstadoFisico());
            ps.setString(6, b.getFactura());
            ps.setString(7, b.getProveedor());
            ps.setString(8, b.getTipoBien());
            ps.setString(9, b.getStatus());

            ps.setInt(10, b.getId());

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void actualizarMultiple(Bien b) {

        String sql = """
            UPDATE bienes
            SET
                descripcion = ?,
                marca = ?,
                modelo = ?,
                proveedor = ?,
                numero_factura = ?,
                estado_fisico = ?,
                tipo_bien = ?,
                status = ?
            WHERE id_bien = ?
        """;

        try (Connection conn = ConexionBD.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, b.getDescripcion());
            ps.setString(2, b.getMarca());
            ps.setString(3, b.getModelo());
            ps.setString(4, b.getProveedor());
            ps.setString(5, b.getFactura());
            ps.setString(6, b.getEstadoFisico());
            ps.setString(7, b.getTipoBien());
            ps.setString(8, b.getStatus());

            ps.setInt(9, b.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void darBajaBien(
            int idBien,
            int idUsuario,
            String motivo,
            String status,
            String areNueva,
            String resNueva
    ) {

        String sqlBien = """
            UPDATE bienes
            SET status = 'BAJA'
            WHERE id_bien = ?
        """;

        String sqlBaja = """
            INSERT INTO bajas (
                id_bien,
                id_usuario,
                motivo,
                fecha_baja
            )
            VALUES (?, ?, ?, CURRENT_TIMESTAMP)
        """;

        try (Connection conn = ConexionBD.conectar()) {

            // =========================
            // UPDATE BIEN
            // =========================
            PreparedStatement psBien =
                    conn.prepareStatement(sqlBien);

            psBien.setInt(1, idBien);

            psBien.executeUpdate();

            // =========================
            // INSERT BAJA
            // =========================
            PreparedStatement psBaja =
                    conn.prepareStatement(sqlBaja);

            psBaja.setInt(1, idBien);
            psBaja.setInt(2, idUsuario);
            psBaja.setString(3, motivo);

            psBaja.executeUpdate();

            // =========================
            // MOVIMIENTO
            // =========================
            registrarMovimiento(
                    idBien,
                    idUsuario,
                    motivo,
                    "BAJA",
                    status,
                    areNueva,
                    resNueva

            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrarMovimiento(
            int idBien,
            int idUsuario,
            String observaciones,
            String tipoMovimiento,
            String status,
            String areNueva,
            String resNueva
    ) {

        String sql = """
            INSERT INTO movimientos (
                id_bien,
                id_usuario,
                fecha_movimiento,
                tipo_movimiento,
                observaciones,
                status,
                nombre_area_nueva,
                nombre_resguardante_nuevo
            )
            VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ? ,?)
        """;

        try (Connection conn = ConexionBD.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBien);
            ps.setInt(2, idUsuario);
            ps.setString(3, tipoMovimiento);
            ps.setString(4, observaciones);
            ps.setString(5, status);
            ps.setString(6, areNueva);
            ps.setString(7, resNueva);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void asignarResguardante(
            int idBien,
            int nuevoResguardante,
            int nuevaArea,
            int idUsuario,
            String observaciones,
            String areaNueva,
            String resNueva
    ) {

        try (Connection conn = ConexionBD.conectar()) {

            // =========================
            // OBTENER DATOS ANTERIORES
            // =========================
            String sqlDatos = """
                SELECT
                    resguardante_id,
                    area_id
                FROM bienes
                WHERE id_bien = ?
            """;

            PreparedStatement psDatos =
                    conn.prepareStatement(sqlDatos);

            psDatos.setInt(1, idBien);

            ResultSet rs = psDatos.executeQuery();

            int resguardanteAnterior = 0;
            int areaAnterior = 0;

            if (rs.next()) {

                resguardanteAnterior =
                        rs.getInt("resguardante_id");

                areaAnterior =
                        rs.getInt("area_id");
            }

            String sqlDatosRes = """
                SELECT nombre_resguardante
                FROM resguardantes
                WHERE id_resguardante = ?
            """;

            PreparedStatement psDatosRes =
                    conn.prepareStatement(sqlDatosRes);

            psDatosRes.setInt(1, resguardanteAnterior);

            ResultSet nr = psDatosRes.executeQuery();

            String resAnterior = "";

            if (nr.next()) {

                resAnterior = nr.getString("nombre_resguardante");

            }

            String sqlDatosAre = """
                SELECT nombre_area
                FROM areas
                WHERE id_area = ?
            """;

            PreparedStatement psDatosAre =
                    conn.prepareStatement(sqlDatosAre);

            psDatosAre.setInt(1, areaAnterior);

            ResultSet na = psDatosAre.executeQuery();

            String areAnterior = "";

            if (na.next()) {

                areAnterior = na.getString("nombre_area");

            }

            // =========================
            // UPDATE BIEN
            // =========================
            String sqlUpdate = """
                UPDATE bienes
                SET
                    resguardante_id = ?,
                    area_id = ?
                WHERE id_bien = ?
            """;

            PreparedStatement psUpdate =
                    conn.prepareStatement(sqlUpdate);

            psUpdate.setInt(1, nuevoResguardante);
            psUpdate.setInt(2, nuevaArea);
            psUpdate.setInt(3, idBien);

            psUpdate.executeUpdate();

            // =========================
            // INSERT MOVIMIENTO
            // =========================
            String sqlMovimiento = """
                INSERT INTO movimientos (
                    id_bien,
                    id_usuario,
                    fecha_movimiento,
                    tipo_movimiento,
                    observaciones,
                    nombre_area_anterior,
                    nombre_area_nueva,
                    nombre_resguardante_anterior,
                    nombre_resguardante_nuevo
                )
                VALUES (
                    ?,
                    ?,
                    CURRENT_TIMESTAMP,
                    'CAMBIO DE RESGUARDO',
                    ?,
                    ?,
                    ?,
                    ?,
                    ?
                )
            """;

            PreparedStatement psMov =
                    conn.prepareStatement(sqlMovimiento);

            psMov.setInt(1, idBien);
            psMov.setInt(2, idUsuario);

            psMov.setString(3, observaciones);

            // 🔥 si vienen null
            if (areaAnterior == 0) {
                psMov.setNull(4, java.sql.Types.INTEGER);
            } else {
                psMov.setString(4, areAnterior);
            }

            psMov.setString(5, areaNueva);

            if (resguardanteAnterior == 0) {
                psMov.setNull(6, java.sql.Types.INTEGER);
            } else {
                psMov.setString(6, resAnterior);
            }

            psMov.setString(7, resNueva);

            psMov.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Bien> buscarBienes(
            String texto
    ) {

        List<Bien> lista =
                new ArrayList<>();

        String sql = """
            SELECT
                b.*,
                a.nombre_area,
                r.nombre_resguardante
            FROM bienes b
            LEFT JOIN areas a
                ON b.area_id = a.id_area
            LEFT JOIN resguardantes r
                ON b.resguardante_id =
                r.id_resguardante
            WHERE b.status <> 'BAJA'
            AND (
                UPPER(
                    COALESCE(
                        b.numero_inventario,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        b.descripcion,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        b.marca,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        b.modelo,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        b.numero_serie,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        b.numero_factura,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        b.proveedor,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        a.nombre_area,''
                    )
                ) LIKE UPPER(?)

                OR

                UPPER(
                    COALESCE(
                        r.nombre_resguardante,''
                    )
                ) LIKE UPPER(?)
            )
            ORDER BY b.id_bien
        """;

        try(Connection conn =
                    ConexionBD.conectar();

            PreparedStatement ps =
                    conn.prepareStatement(sql)) {

            String filtro =
                    "%" + texto + "%";

            for(int i = 1; i <= 9; i++) {

                ps.setString(i, filtro);
            }

            ResultSet rs =
                    ps.executeQuery();

            while(rs.next()) {

                Bien b = new Bien();

                b.setId(rs.getInt("id_bien"));
                b.setNumeroInventario(rs.getString("numero_inventario"));
                b.setTipoAdquisicion(rs.getString("tipo_adquisicion"));
                b.setDescripcion(rs.getString("descripcion"));
                b.setMarca(rs.getString("marca"));
                b.setModelo(rs.getString("modelo"));
                b.setNumeroSerie(rs.getString("numero_serie"));

                // 🔥 NUEVOS CAMPOS
                b.setEstadoFisico(rs.getString("estado_fisico"));
                b.setFactura(rs.getString("numero_factura"));
                b.setProveedor(rs.getString("proveedor"));
                b.setTipoBien(rs.getString("tipo_bien"));

                b.setArea(rs.getString("nombre_area"));
                b.setResguardante(rs.getString("nombre_resguardante"));

                b.setFechaAlta(rs.getTimestamp("fecha_alta").toLocalDateTime());
                b.setStatus(rs.getString("status"));

                lista.add(b);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }

    public String generarConsecutivoEspecial(
            String prefijo
    ) {

        String sql = """
            SELECT numero_inventario
            FROM bienes
            WHERE numero_inventario LIKE ?
            ORDER BY id_bien DESC
            LIMIT 1
        """;

        try(Connection conn =
                    ConexionBD.conectar();
            PreparedStatement ps =
                    conn.prepareStatement(sql)) {

            ps.setString(
                    1,
                    prefijo + " - %"
            );

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                String ultimo =
                        rs.getString(
                                "numero_inventario"
                        );

                String numero =
                        ultimo.substring(
                                ultimo.lastIndexOf("-") + 1
                        ).trim();

                int consecutivo =
                        Integer.parseInt(numero);

                consecutivo++;

                return String.format(
                        "%s - %05d",
                        prefijo,
                        consecutivo
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return String.format(
                "%s - %05d",
                prefijo,
                1
        );
    }

    public boolean existeNumeroInventario(
            String numeroInventario) {

        String sql = """
            SELECT COUNT(*)
            FROM bienes
            WHERE numero_inventario = ?
        """;

        try(Connection conn = ConexionBD.conectar();
            PreparedStatement ps =
                    conn.prepareStatement(sql)) {

            ps.setString(1, numeroInventario);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}