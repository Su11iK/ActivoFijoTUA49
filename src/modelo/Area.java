package modelo;

public class Area {

    private int id;
    private String nombre;
    private boolean status_area;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isStatusArea() {
        return status_area;
    }

    public void setStatusArea(boolean status_area) {
        this.status_area = status_area;
    }
}