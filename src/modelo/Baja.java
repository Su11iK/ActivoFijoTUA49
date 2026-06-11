package modelo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Baja {

    private int id;

    private String numeroInventario;

    private String usuario;

    private LocalDateTime fechaBaja;

    private String motivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroInventario() {
        return numeroInventario;
    }

    public void setNumeroInventario(String numeroInventario) {
        this.numeroInventario = numeroInventario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}