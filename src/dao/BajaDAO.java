package dao;

import conexion.ConexionBD;
import modelo.Baja;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BajaDAO {

    public List<Baja> listarBajas() {

        List<Baja> lista =
                new ArrayList<>();

        String sql = """
            SELECT

                ba.id_baja,

                b.numero_inventario,

                u.nombre_usuario,

                ba.fecha_baja,

                ba.motivo

            FROM bajas ba

            LEFT JOIN bienes b
                ON ba.id_bien = b.id_bien

            LEFT JOIN usuarios u
                ON ba.id_usuario = u.id_usuario

            ORDER BY ba.fecha_baja DESC
        """;

        try(Connection conn =
                    ConexionBD.conectar();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()) {

            while(rs.next()) {

                Baja b = new Baja();

                b.setId(
                        rs.getInt("id_baja"));

                b.setNumeroInventario(
                        rs.getString(
                                "numero_inventario"));

                b.setUsuario(
                        rs.getString(
                                "nombre_usuario"));

                b.setFechaBaja(
                        rs.getTimestamp(
                                "fecha_baja").toLocalDateTime());

                b.setMotivo(
                        rs.getString(
                                "motivo"));

                lista.add(b);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return lista;
    }
}