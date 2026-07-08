package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:postgresql://10.10.62.64:5432/inventario_tua49";
    private static final String USER = "postgres";
    private static final String PASSWORD = "4321"; // cambia si es necesario

    public static Connection conectar() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("✅ Conexión exitosa a PostgreSQL");
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión");
            e.printStackTrace();
        }

        return conn;
    }
}