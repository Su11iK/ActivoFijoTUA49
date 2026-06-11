package modelo;

import java.time.LocalDateTime;

public class Movimiento {

    private int id;
    private LocalDateTime fechaMovimiento;
    private String numeroInventario;
    private String usuario;
    private String areaAnterior;
    private String areaNueva;
    private String resguardanteAnterior;
    private String resguardanteNuevo;
    private String tipoMovimiento;
    private String observaciones;
    private String status;

    public int getId() {
        return id;
    }
    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }
    public String getNumeroInventario() {
        return numeroInventario;
    }
    public String getUsuario() {
        return usuario;
    }
    public String getAreaAnterior() {
        return areaAnterior;
    }
    public String getAreaNueva() {
        return areaNueva;
    }
    public String getResguardanteAnterior() {
        return resguardanteAnterior;
    }
    public String getResguardanteNuevo() {
        return resguardanteNuevo;
    }
    public String getTipoMovimiento() {
        return tipoMovimiento;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
    public void setNumeroInventario(String numeroInventario) {
        this.numeroInventario = numeroInventario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public void setAreaAnterior(String areaAnterior) {
        this.areaAnterior = areaAnterior;
    }
    public void setAreaNueva(String areaNueva) {
        this.areaNueva = areaNueva;
    }
    public void setResguardanteAnterior(String resguardanteAnterior) {
        this.resguardanteAnterior = resguardanteAnterior;
    }
    public void setResguardanteNuevo(String resguardanteNuevo) {
        this.resguardanteNuevo = resguardanteNuevo;
    }
    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}