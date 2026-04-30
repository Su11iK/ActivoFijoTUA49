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
                b.descripcion,
                b.marca,
                b.modelo,
                b.numero_serie,
                b.estado_fisico,
                b.numero_factura,
                b.proveedor,
                b.tipo_adquisicion,
                b.fecha_alta,
                b.status,
                a.nombre_area,
                r.nombre_resguardante
            FROM bienes b
            LEFT JOIN areas a ON b.area_id = a.id_area
            LEFT JOIN resguardantes r ON b.resguardante_id = r.id_resguardante
            """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bien b = new Bien();
                b.setId(rs.getInt("id_bien"));
                b.setNumeroInventario(rs.getString("numero_inventario"));
                b.setDescripcion(rs.getString("descripcion"));
                b.setMarca(rs.getString("marca"));
                b.setModelo(rs.getString("modelo"));
                b.setNumeroSerie(rs.getString("numero_serie"));

                lista.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}