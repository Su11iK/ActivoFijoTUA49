package conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {

        Path base;

        try {
            base = Paths.get(
                Config.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
            ).getParent();
        } catch (URISyntaxException e) {
            throw new RuntimeException("No se pudo obtener la ruta de la aplicación.", e);
        }

        try {
            Path ruta = base.resolve("config").resolve("config.properties");
            FileInputStream archivo = new FileInputStream(ruta.toFile());
            properties.load(archivo);
            archivo.close();
        } catch (IOException e) {
            System.out.println("No se pudo cargar el archivo de configuración.");
            e.printStackTrace();
        }
    }

    public static String getHost() {
        return properties.getProperty("host");
    }

    public static String getPuerto() {
        return properties.getProperty("puerto");
    }

    public static String getBaseDatos() {
        return properties.getProperty("base_datos");
    }

    public static String getUsuario() {
        return properties.getProperty("usuario");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
}
