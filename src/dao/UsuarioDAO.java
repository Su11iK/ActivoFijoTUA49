package dao;

import conexion.ConexionBD;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario login(String nombre, String password) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND password = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre_usuario"));
                usuario.setStatus(rs.getString("status_usuario"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }
}