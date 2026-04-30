package modelo;

public class Usuario {
    private int id;
    private String nombre;
    private String password;
    private String status;

    public Usuario() {}

    public Usuario(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}