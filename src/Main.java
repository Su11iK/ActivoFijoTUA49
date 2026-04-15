import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/inventario_tua49";
        String user = "postgres";
        String password = "4321";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}