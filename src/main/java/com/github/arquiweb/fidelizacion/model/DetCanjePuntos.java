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

    @JoinColumn(name = "id_canje", referencedColumnName = "id")
    @OneToOne(optional = false)
    private CanjePuntos idCanje;

    @JoinColumn(name = "id_bolsa_puntos", referencedColumnName = "id")
    @OneToOne(optional = false)
    private BolsaPuntos idBolsaPuntos;

    @Column(name = "puntaje_utilizado")
    @Basic(optional = false)
    private int puntajeUtilizado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CanjePuntos getIdCanje() {
        return idCanje;
    }

    public void setIdCanje(CanjePuntos idCanje) {
        this.idCanje = idCanje;
    }

    public BolsaPuntos getIdBolsaPuntos() {
        return idBolsaPuntos;
    }

    public void setIdBolsaPuntos(BolsaPuntos idBolsaPuntos) {
        this.idBolsaPuntos = idBolsaPuntos;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }
}
