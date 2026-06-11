package dao;

import conexion.ConexionBD;
import modelo.Movimiento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDAO {

    public List<Movimiento> listarMovimientos() {

        List<Movimiento> lista =
                new ArrayList<>();

        String sql = """
            SELECT

                m.id_movimiento,
                m.fecha_movimiento,

                b.numero_inventario,

                u.nombre_usuario,

                aa.nombre_area AS area_anterior,
                an.nombre_area AS area_nueva,

                ra.nombre_resguardante AS resguardante_anterior,
                rn.nombre_resguardante AS resguardante_nuevo,

                m.tipo_movimiento,
                m.observaciones,
                m.status

            FROM movimientos m

            LEFT JOIN bienes b
                ON m.id_bien = b.id_bien

            LEFT JOIN usuarios u
                ON m.id_usuario = u.id_usuario

            LEFT JOIN areas aa
                ON m.area_anterior = aa.id_area

            LEFT JOIN areas an
                ON m.area_nueva = an.id_area

            LEFT JOIN resguardantes ra
                ON m.resguardante_anterior =
                   ra.id_resguardante

            LEFT JOIN resguardantes rn
                ON m.resguardante_nuevo =
                   rn.id_resguardante

            ORDER BY m.fecha_movimiento DESC
        """;

        try(Connection conn =
                    ConexionBD.conectar();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                Movimiento m =
                        new Movimiento();

                m.setId(
                    rs.getInt("id_movimiento"));

                m.setFechaMovimiento(
                    rs.getTimestamp("fecha_movimiento").toLocalDateTime());

                m.setNumeroInventario(
                    rs.getString("numero_inventario"));

                m.setUsuario(
                    rs.getString("nombre_usuario"));

                m.setAreaAnterior(
                    rs.getString("area_anterior"));

                m.setAreaNueva(
                    rs.getString("area_nueva"));

                m.setResguardanteAnterior(
                    rs.getString(
                        "resguardante_anterior"));

                m.setResguardanteNuevo(
                    rs.getString(
                        "resguardante_nuevo"));

                m.setTipoMovimiento(
                    rs.getString(
                        "tipo_movimiento"));

                m.setObservaciones(
                    rs.getString(
                        "observaciones"));

                m.setStatus(
                    rs.getString("status")
                );

                lista.add(m);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}