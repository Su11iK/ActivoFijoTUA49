package dao;

import conexion.ConexionBD;
import modelo.Resguardante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResguardanteDAO {

    // =========================
    // LISTAR
    // =========================
    public List<Resguardante> listarResguardantes() {

        List<Resguardante> lista =
                new ArrayList<>();

        String sql = """
            SELECT
                r.id_resguardante,
                r.nombre_resguardante,
                r.puesto,
                r.id_area,
                a.nombre_area,
                r.status_resguardante
            FROM resguardantes r
            LEFT JOIN areas a
                ON r.id_area = a.id_area
            WHERE r.status_resguardante = TRUE
            ORDER BY r.nombre_resguardante
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps =
                     conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Resguardante r =
                        new Resguardante();

                r.setId(
                        rs.getInt("id_resguardante"));

                r.setNombre(
                        rs.getString("nombre_resguardante"));

                r.setPuesto(
                        rs.getString("puesto"));

                r.setIdArea(
                        rs.getInt("id_area"));

                r.setNombreArea(
                        rs.getString("nombre_area"));

                r.setStatusResguardante(
                        rs.getBoolean("status_resguardante"));

                lista.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // INSERTAR
    // =========================
    public void insertarResguardante(
            String nombre,
            String puesto,
            int idArea
    ) {

        String sql = """
            INSERT INTO resguardantes (
                nombre_resguardante,
                puesto,
                id_area,
                status_resguardante
            )
            VALUES (?, ?, ?, TRUE)
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, puesto);
            ps.setInt(3, idArea);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // EDITAR
    // =========================
    public void editarResguardante(
            int id,
            String nombre,
            String puesto,
            int idArea
    ) {

        String sql = """
            UPDATE resguardantes
            SET
                nombre_resguardante = ?,
                puesto = ?,
                id_area = ?
            WHERE id_resguardante = ?
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, puesto);
            ps.setInt(3, idArea);
            ps.setInt(4, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // ELIMINAR LOGICO
    // =========================
    public void eliminarResguardante(int id) {

        String sql = """
            UPDATE resguardantes
            SET status_resguardante = FALSE
            WHERE id_resguardante = ?
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}