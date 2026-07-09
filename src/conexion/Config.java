package conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    static {
        try {
            String ruta = System.getProperty("user.dir") + File.separator + "config" + File.separator + "config.properties";
            FileInputStream archivo = new FileInputStream(ruta);
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
