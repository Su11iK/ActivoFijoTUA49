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
    public void editarArea(
        int id,
        String nombreAnterior,
        String nombre,
        String observaciones,
        boolean cambioNombre
    ) {

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

            String sqlSelect = """
                SELECT
                    id_bien
                FROM bienes
                WHERE area_id = ?
            """;

            PreparedStatement psSelect =
                    conn.prepareStatement(sqlSelect);

            psSelect.setInt(1, id);

            ResultSet rs =
                    psSelect.executeQuery();

            if (cambioNombre) {
                while(rs.next()) {

                    int idBien =
                            rs.getInt("id_bien");

                    String sqlMov = """
                        INSERT INTO movimientos(
                            id_bien,
                            id_usuario,
                            fecha_movimiento,
                            tipo_movimiento,
                            nombre_area_anterior,
                            nombre_area_nueva,
                            observaciones
                        )
                        VALUES(
                            ?,
                            1,
                            CURRENT_TIMESTAMP,
                            'EDICION DE AREA',
                            ?,
                            ?,
                            ?
                        )
                    """;

                    PreparedStatement psMov =
                            conn.prepareStatement(sqlMov);

                    psMov.setInt(1, idBien);
                    psMov.setString(2, nombreAnterior);
                    psMov.setString(3, nombre);
                    psMov.setString(4, observaciones);

                    psMov.executeUpdate();
                }
            }
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

        String sqlR = """
            UPDATE resguardantes
            SET id_area = NULL
            WHERE id_area = ?
        """;



        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             PreparedStatement ps1 = conn.prepareStatement(sqlR)) {

            ps.setInt(1, id);
            ps1.setInt(1, id);

            ps.executeUpdate();
            ps1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean tieneBienesAsignados(int idArea) {

        String sql = """
            SELECT COUNT(*)
            FROM bienes
            WHERE area_id = ?
        """;

        try(Connection conn = ConexionBD.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idArea);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void quitarAreaDeBienes(
            int idArea,
            int idUsuario,
            String observaciones,
            String areAnterior
    ) {

        String sqlSelect = """
            SELECT id_bien
            FROM bienes
            WHERE area_id = ?
        """;

        String sqlUpdate = """
            UPDATE bienes
            SET area_id = NULL
            WHERE area_id = ?
        """;

        try(Connection conn = ConexionBD.conectar()) {

            PreparedStatement ps =
                    conn.prepareStatement(sqlSelect);

            ps.setInt(1, idArea);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {

                int idBien =
                        rs.getInt("id_bien");

                registrarMovimientoArea(
                        conn,
                        idBien,
                        idUsuario,
                        idArea,
                        observaciones,
                        areAnterior
                );
            }

            PreparedStatement update =
                    conn.prepareStatement(sqlUpdate);

            update.setInt(1, idArea);

            update.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void registrarMovimientoArea(
            Connection conn,
            int idBien,
            int idUsuario,
            int areaAnterior,
            String observaciones,
            String areAnterior
    ) throws SQLException {

        String sql = """
            INSERT INTO movimientos(
                id_bien,
                id_usuario,
                fecha_movimiento,
                tipo_movimiento,
                observaciones,
                nombre_area_anterior,
                nombre_area_nueva
            )
            VALUES(
                ?,
                ?,
                CURRENT_TIMESTAMP,
                'BAJA AREA',
                ?,
                ?,
                '<vacío>'
            )
        """;

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, idBien);
        ps.setInt(2, idUsuario);
        ps.setString(3, observaciones);
        ps.setString(4, areAnterior);

        ps.executeUpdate();
    }

}