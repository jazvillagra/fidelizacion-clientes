package com.github.arquiweb.fidelizacion.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bolsa_puntos")
public class BolsaPuntos {
    @Id
    @Column(name = "id")
    @Basic(optional = false)
    @GeneratedValue(generator = "bolsaPuntosSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "bolsaPuntosSec", sequenceName = "bolsa_puntos_sec", allocationSize = 0)
    private int id;

    @Column(name = "id_cliente")
    @Basic(optional = false)
    private int idCliente;

    @Column(name = "fecha_asignacion")
    @Basic(optional = false)
    private Date fechaAsignacion;

    @Column(name = "fecha_vencimiento")
    @Basic(optional = false)
    private Date fechaVencimiento;

    @Column(name = "puntaje_asignado")
    @Basic(optional = false)
    private int puntajeAsignado;

    @Column(name = "puntaje_utilizado")
    @Basic(optional = false)
    private int puntajeUtilizado;

    @Column(name = "saldo")
    @Basic(optional = false)
    private int saldo;

    @Column(name = "monto")
    @Basic(optional = false)
    private int monto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getPuntajeAsignado() {
        return puntajeAsignado;
    }

    public void setPuntajeAsignado(int puntajeAsignado) {
        this.puntajeAsignado = puntajeAsignado;
    }

    public int getPuntajeUtilizado() {
        return puntajeUtilizado;
    }

    public void setPuntajeUtilizado(int puntajeUtilizado) {
        this.puntajeUtilizado = puntajeUtilizado;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }
}
