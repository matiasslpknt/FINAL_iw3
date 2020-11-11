package ar.edu.iua.model;

import java.io.Serializable;


public class OrdenSurtidorDTO implements Serializable {


    private long idOrden;
    private double temperatura;
    private double masaAcumulada;
    private String fecha;

    public long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(long idOrden) {
        this.idOrden = idOrden;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getMasaAcumulada() {
        return masaAcumulada;
    }

    public void setMasaAcumulada(double masaAcumulada) {
        this.masaAcumulada = masaAcumulada;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public OrdenSurtidorDTO(long idOrden, double temperatura, double masaAcumulada) {
        this.idOrden = idOrden;
        this.temperatura = temperatura;
        this.masaAcumulada = masaAcumulada;
    }
}
