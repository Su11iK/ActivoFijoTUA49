package modelo;

public class Resguardante {

    private int id;
    private String nombre;
    private String puesto;
    private int idArea;
    private String nombreArea;
    private boolean statusResguardante;

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

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public boolean isStatusResguardante() {
        return statusResguardante;
    }

    public void setStatusResguardante(boolean statusResguardante) {
        this.statusResguardante = statusResguardante;
    }

    @Override
    public String toString() {
        return nombre;
    }
}