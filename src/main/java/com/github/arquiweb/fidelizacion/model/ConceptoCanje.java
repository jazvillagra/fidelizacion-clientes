package com.github.arquiweb.fidelizacion.model;

import javax.persistence.*;

@Entity
@Table(name = "concepto_canje")
public class ConceptoCanje {

    @Id
    @Column(name = "id")
    @Basic(optional = false)
    @GeneratedValue(generator = "conceptoCanjeSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "conceptoCanjeSec", sequenceName = "concepto_canje_sec", allocationSize = 0)
    private int id;

    @Column(name = "desc_concepto")
    @Basic(optional = false)
    private int descConcepto;

    @Column(name = "puntos_requeridos", length = 200)
    @Basic(optional = false)
    private String puntosRequeridos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescConcepto() {
        return descConcepto;
    }

    public void setDescConcepto(int descConcepto) {
        this.descConcepto = descConcepto;
    }

    public String getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public void setPuntosRequeridos(String puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }
}
