package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String url = "jdbc:postgresql://"
        + Config.getHost()
        + ":"
        + Config.getPuerto()
        + "/"
        + Config.getBaseDatos();

    public static Connection conectar() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url,
                Config.getUsuario(),
                Config.getPassword());
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión");
            e.printStackTrace();
        }

        return conn;
    }
}