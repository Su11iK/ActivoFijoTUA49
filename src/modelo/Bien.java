package modelo;

public class Bien {

    private int id;
    private String numeroInventario;
    private String tipoAdquisicion;
    private String descripcion;
    private String marca;
    private String modelo;
    private String numeroSerie;
    private String estadoFisico;
    private String factura;
    private String proveedor;
    private String tipoBien;
    private String area;
    private String resguardante;
    private String fechaAlta;
    private String status;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroInventario() { return numeroInventario; }
    public void setNumeroInventario(String numeroInventario) { this.numeroInventario = numeroInventario; }

    public String getTipoAdquisicion() { return tipoAdquisicion; }
    public void setTipoAdquisicion(String tipoAdquisicion) { this.tipoAdquisicion = tipoAdquisicion; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }
    
    public String getEstadoFisico() { return estadoFisico; }
    public void setEstadoFisico(String estadoFisico) { this.estadoFisico = estadoFisico; }
    
    public String getFactura() { return factura; }
    public void setFactura(String factura) { this.factura = factura; }
    
    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
    
    public String getTipoBien() { return tipoBien; }
    public void setTipoBien(String tipoBien) { this.tipoBien = tipoBien; }
    
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    
    public String getResguardante() { return resguardante; }
    public void setResguardante(String resguardante) { this.resguardante = resguardante; }
    
    public String getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(String fechaAlta) { this.fechaAlta = fechaAlta; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
}