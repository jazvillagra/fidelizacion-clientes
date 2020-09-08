package com.github.arquiweb.fidelizacion.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "canje_puntos")
public class CanjePuntos {
    @Id
    @Column(name = "id")
    @Basic(optional = false)
    @GeneratedValue(generator = "canjePuntosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "canjePuntosSec", sequenceName = "canje_puntos_sec", allocationSize = 0)
    private int id;

    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Cliente idCliente;

    @Column(name = "fecha_uso")
    @Basic(optional = false)
    private Date fechaUso;

    @Column(name = "puntaje_utilizado")
    @Basic(optional = false)

    @OneToOne(mappedBy = "det_canje_puntos")
    private DetCanjePuntos detCanjePuntos;

    private int puntajeUtilizado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaUso() {
        return fechaUso;
    }

    public void setFechaUso(Date fechaUso) {
        this.fechaUso = fechaUso;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public DetCanjePuntos getDetCanjePuntos() {
        return detCanjePuntos;
    }

    public void setDetCanjePuntos(DetCanjePuntos detCanjePuntos) {
        this.detCanjePuntos = detCanjePuntos;
    }
}
