package com.github.arquiweb.fidelizacion.model;

import javax.persistence.*;

@Entity
@Table(name = "det_canje_puntos")
public class DetCanjePuntos {
    @Id
    @Column(name = "id")
    @Basic(optional = false)
    @GeneratedValue(generator = "detCanjePuntosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "detCanjePuntosSec", sequenceName = "det_canje_puntos_sec", allocationSize = 0)
    private int id;

    @Column(name = "id_canje")
    private int idCanje;

    @Column(name = "id_bolsa_puntos")
    @Basic(optional = false)
    private int idBolsaPuntos;

    @Column(name = "puntaje_utilizado")
    @Basic(optional = false)
    private int puntajeUtilizado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCanje() {
        return idCanje;
    }

    public void setIdCanje(int idCanje) {
        this.idCanje = idCanje;
    }

    public int getIdBolsaPuntos() {
        return idBolsaPuntos;
    }

    public void setIdBolsaPuntos(int idBolsaPuntos) {
        this.idBolsaPuntos = idBolsaPuntos;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }
}
