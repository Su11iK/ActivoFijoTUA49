package dao;

import conexion.ConexionBD;
import modelo.Area;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {

    // =========================
    // LISTAR
    // =========================
    public List<Area> listarAreas() {

        List<Area> lista = new ArrayList<>();

        String sql = """
            SELECT *
            FROM areas
            WHERE status_area = TRUE
            ORDER BY nombre_area
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Area a = new Area();

                a.setId(rs.getInt("id_area"));
                a.setNombre(rs.getString("nombre_area"));
                a.setStatusArea(rs.getBoolean("status_area"));

                lista.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // INSERTAR
    // =========================
    public void insertarArea(String nombre) {

        String sql = """
            INSERT INTO areas (
                nombre_area,
                status_area
            )
            VALUES (?, TRUE)
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // EDITAR
    // =========================
    public void editarArea(int id, String nombre) {

        String sql = """
            UPDATE areas
            SET nombre_area = ?
            WHERE id_area = ?
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // ELIMINAR LOGICO
    // =========================
    public void eliminarArea(int id) {

        String sql = """
            UPDATE areas
            SET status_area = FALSE
            WHERE id_area = ?
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}