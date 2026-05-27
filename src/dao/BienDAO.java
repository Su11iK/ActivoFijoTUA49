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
                tipo_bien = ?
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

            ps.setInt(9, b.getId());

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
                tipo_bien = ?
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

            ps.setInt(8, b.getId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void darBajaBien(
            int idBien,
            int idUsuario,
            String motivo
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
                    "Bien dado de baja",
                    "BAJA"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrarMovimiento(
            int idBien,
            int idUsuario,
            String observaciones,
            String tipoMovimiento
    ) {

        String sql = """
            INSERT INTO movimientos (
                id_bien,
                id_usuario,
                fecha_movimiento,
                tipo_movimiento,
                observaciones
            )
            VALUES (?, ?, CURRENT_TIMESTAMP, ?, ?)
        """;

        try (Connection conn = ConexionBD.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBien);
            ps.setInt(2, idUsuario);
            ps.setString(3, tipoMovimiento);
            ps.setString(4, observaciones);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}