package ar.edu.iua.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orden")
@SqlResultSetMapping(
        name = "ordensurtidor",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "o.idOrden", type = String.class),
                                @ColumnResult(name = "o.temperatura", type = String.class),
                                @ColumnResult(name = "o.caudal", type = double.class)
                        },
                        targetClass = OrdenSurtidorDTO.class
                )
        }
)
public class Orden implements Serializable {

    private static final long serialVersionUID = -6842932053341232133L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String numeroOrden;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "camion_id")
    private Camion camion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @OneToOne(cascade = CascadeType.ALL)
    private Conciliacion conciliacion;

//    @OneToMany(targetEntity = OrdenDetalle.class, mappedBy = "orden", fetch = FetchType.LAZY)
//    @JsonBackReference
//    private List<OrdenDetalle> ordenDetalleList;

    private double masaAcumulada;

    private double densidad;

    private double temperatura;

    private double caudal;

    private Date fechaUltimoAlmacenamiento;

    private double preset;

    private int estado;

    private Date fechaGeneracionOrden;

    private Date fechaPrevistaCarga;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Conciliacion getConciliacion() {
        return conciliacion;
    }

    public void setConciliacion(Conciliacion conciliacion) {
        this.conciliacion = conciliacion;
    }

//    public List<OrdenDetalle> getOrdenDetalleList() {
//        return ordenDetalleList;
//    }
//
//    public void setOrdenDetalleList(List<OrdenDetalle> ordenDetalleList) {
//        this.ordenDetalleList = ordenDetalleList;
//    }

    public double getMasaAcumulada() {
        return masaAcumulada;
    }

    public void setMasaAcumulada(double masaAcumulada) {
        this.masaAcumulada = masaAcumulada;
    }

    public double getDensidad() {
        return densidad;
    }

    public void setDensidad(double densidad) {
        this.densidad = densidad;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getCaudal() {
        return caudal;
    }

    public void setCaudal(double caudal) {
        this.caudal = caudal;
    }

    public Date getFechaUltimoAlmacenamiento() {
        return fechaUltimoAlmacenamiento;
    }

    public void setFechaUltimoAlmacenamiento(Date fechaUltimoAlmacenamiento) {
        this.fechaUltimoAlmacenamiento = fechaUltimoAlmacenamiento;
    }

    public double getPreset() {
        return preset;
    }

    public void setPreset(double preset) {
        this.preset = preset;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getFechaGeneracionOrden() {
        return fechaGeneracionOrden;
    }

    public void setFechaGeneracionOrden(Date fechaGeneracionOrden) {
        this.fechaGeneracionOrden = fechaGeneracionOrden;
    }

    public Date getFechaPrevistaCarga() {
        return fechaPrevistaCarga;
    }

    public void setFechaPrevistaCarga(Date fechaPrevistaCarga) {
        this.fechaPrevistaCarga = fechaPrevistaCarga;
    }
}
