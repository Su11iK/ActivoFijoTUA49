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

                m.nombre_area_anterior,
                m.nombre_area_nueva,

                m.nombre_resguardante_anterior,
                m.nombre_resguardante_nuevo,

                m.tipo_movimiento,
                m.observaciones,
                
                m.status,
                m.status_anterior

            FROM movimientos m

            LEFT JOIN bienes b
                ON m.id_bien = b.id_bien

            LEFT JOIN usuarios u
                ON m.id_usuario = u.id_usuario

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
                    rs.getString("nombre_area_anterior"));

                m.setAreaNueva(
                    rs.getString("nombre_area_nueva"));

                m.setResguardanteAnterior(
                    rs.getString(
                        "nombre_resguardante_anterior"));

                m.setResguardanteNuevo(
                    rs.getString(
                        "nombre_resguardante_nuevo"));

                m.setTipoMovimiento(
                    rs.getString(
                        "tipo_movimiento"));

                m.setObservaciones(
                    rs.getString(
                        "observaciones"));

                m.setStatus(
                    rs.getString("status")
                );

                m.setStatusAnterior(
                    rs.getString("status_anterior")
                );

                lista.add(m);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}