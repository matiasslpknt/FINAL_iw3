package ar.edu.iua.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conciliacion")
public class Conciliacion implements Serializable {

    private static final long serialVersionUID = -1609250874892904470L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double pesajeInicial;
    private double pesajeFinal;
    private double cantidadCargada;
    private double pesoNeto;
    private double diferencia;
    private double promedioTemperatura;
    private double promedioDensidad;
    private double promedioCaudal;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPesajeInicial() {
        return pesajeInicial;
    }

    public void setPesajeInicial(double pesajeInicial) {
        this.pesajeInicial = pesajeInicial;
    }

    public double getPesajeFinal() {
        return pesajeFinal;
    }

    public void setPesajeFinal(double pesajeFinal) {
        this.pesajeFinal = pesajeFinal;
    }

    public double getCantidadCargada() {
        return cantidadCargada;
    }

    public void setCantidadCargada(double cantidadCargada) {
        this.cantidadCargada = cantidadCargada;
    }

    public double getPesoNeto() {
        return pesoNeto;
    }

    public void setPesoNeto(double pesoNeto) {
        this.pesoNeto = pesoNeto;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }

    public double getPromedioTemperatura() {
        return promedioTemperatura;
    }

    public void setPromedioTemperatura(double promedioTemperatura) {
        this.promedioTemperatura = promedioTemperatura;
    }

    public double getPromedioDensidad() {
        return promedioDensidad;
    }

    public void setPromedioDensidad(double promedioDensidad) {
        this.promedioDensidad = promedioDensidad;
    }

    public double getPromedioCaudal() {
        return promedioCaudal;
    }

    public void setPromedioCaudal(double promedioCaudal) {
        this.promedioCaudal = promedioCaudal;
    }
}
