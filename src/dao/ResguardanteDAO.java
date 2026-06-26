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
            String nombreAnterior,
            String nombre,
            String puesto,
            String observaciones,
            boolean cambioNombre
    ) {

        String sql = """
            UPDATE resguardantes
            SET
                nombre_resguardante = ?,
                puesto = ?
            WHERE id_resguardante = ?
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, puesto);
            ps.setInt(3, id);

            ps.executeUpdate();

            String sqlSelect = """
                SELECT
                    id_bien
                FROM bienes
                WHERE resguardante_id = ?
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
                            nombre_resguardante_anterior,
                            nombre_resguardante_nuevo,
                            observaciones
                        )
                        VALUES(
                            ?,
                            1,
                            CURRENT_TIMESTAMP,
                            'EDICION DE RESGUARDANTE',
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
    // ASIGNAR
    // =========================
    public void asignarResguardante(
            int id,
            int idArea,
            String observaciones,
            String areAnterior,
            String areNuevo,
            boolean cambioArea
    ) {

        String sql = """
            UPDATE resguardantes
            SET
                id_area = ?
            WHERE id_resguardante = ?
        """;

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps =
                     conn.prepareStatement(sql)) {

            ps.setInt(1, idArea);
            ps.setInt(2, id);

            ps.executeUpdate();

            String sqlSelect = """
                SELECT
                    id_bien,
                    area_id
                FROM bienes
                WHERE resguardante_id = ?
            """;

            PreparedStatement psSelect =
                    conn.prepareStatement(sqlSelect);

            psSelect.setInt(1, id);

            ResultSet rs =
                    psSelect.executeQuery();

            String sqlBienes = """
                UPDATE bienes
                SET area_id = ?
                WHERE resguardante_id = ?
            """;

            if (cambioArea) {
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
                            'CAMBIO DE AREA',
                            ?,
                            ?,
                            ?
                        )
                    """;

                    PreparedStatement psMov =
                            conn.prepareStatement(sqlMov);

                    psMov.setInt(1, idBien);
                    psMov.setString(2, areAnterior);
                    psMov.setString(3, areNuevo);
                    psMov.setString(4, observaciones);

                    psMov.executeUpdate();
                }
            }

            PreparedStatement psBienes =
                    conn.prepareStatement(sqlBienes);

            psBienes.setInt(1, idArea);
            psBienes.setInt(2, id);

            psBienes.executeUpdate();

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

    public List<Resguardante> listarActivos() {
        return listarResguardantes();
    }

    public boolean tieneBienesAsignados(
            int idResguardante
    ) {

        String sql = """
            SELECT COUNT(*)
            FROM bienes
            WHERE resguardante_id = ?
        """;

        try(Connection conn = ConexionBD.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idResguardante);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void quitarResguardanteDeBienes(
            int idResguardante,
            int idUsuario,
            String observaciones,
            String resAnterior
    ) {

        try (Connection conn = ConexionBD.conectar()) {

            String sqlBienes = """
                SELECT id_bien
                FROM bienes
                WHERE resguardante_id = ?
            """;

            PreparedStatement psBienes =
                    conn.prepareStatement(sqlBienes);

            psBienes.setInt(1, idResguardante);

            ResultSet rs = psBienes.executeQuery();

            while(rs.next()) {

                int idBien =
                        rs.getInt("id_bien");

                String sqlMov = """
                    INSERT INTO movimientos(
                        id_bien,
                        id_usuario,
                        fecha_movimiento,
                        tipo_movimiento,
                        observaciones,
                        nombre_resguardante_anterior
                    )
                    VALUES(
                        ?,
                        ?,
                        CURRENT_TIMESTAMP,
                        'BAJA RESGUARDANTE',
                        ?,
                        ?
                    )
                """;

                PreparedStatement psMov =
                        conn.prepareStatement(sqlMov);

                psMov.setInt(1, idBien);
                psMov.setInt(2, idUsuario);
                psMov.setString(3, observaciones);
                psMov.setString(4, resAnterior);

                psMov.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = """
            UPDATE bienes
            SET resguardante_id = NULL
            WHERE resguardante_id = ?
        """;

        try(Connection conn = ConexionBD.conectar();
            PreparedStatement ps =
                    conn.prepareStatement(sql)) {

            ps.setInt(1, idResguardante);

            ps.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}