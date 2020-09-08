package com.github.arquiweb.fidelizacion.model;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vencimiento_puntos")
public class VencimientoPuntos {

    @Id
    @Column(name = "id")
    @Basic(optional = false)
    @GeneratedValue(generator = "vencimientoPuntosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vencimientoPuntosSec", sequenceName = "vencimiento_puntos_sec", allocationSize = 0)
    private int id;

    @Column(name = "fecha_ini_validez")
    private Date fechaIniValidez;

    @Column(name = "fecha_fin_validez")
    private Date fechaFinValidez;

    @Column(name = "duracion")
    private Date duracion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFechaIniValidez() {
        return fechaIniValidez;
    }

    public void setFechaIniValidez(Date fechaIniValidez) {
        this.fechaIniValidez = fechaIniValidez;
    }

    public Date getFechaFinValidez() {
        return fechaFinValidez;
    }

    public void setFechaFinValidez(Date fechaFinValidez) {
        this.fechaFinValidez = fechaFinValidez;
    }

    public Date getDuracion() {
        return duracion;
    }

    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }
}
