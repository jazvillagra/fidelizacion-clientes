package com.github.arquiweb.fidelizacion.model;

import javax.persistence.*;

@Entity
@Table(name = "regla")
public class Regla {

    @Id
    @Column(name = "id")
    @Basic(optional = false)
    @GeneratedValue(generator = "reglaSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reglaSec", sequenceName = "regla_sec", allocationSize = 0)
    private int id;

    @Column(name = "limite_max")
    @Basic(optional = false)
    private Integer limiteMax;

    @Column(name = "limite_min")
    @Basic(optional = false)
    private Integer limiteMin;

    @Column(name = "monto_equivalencia")
    @Basic(optional = false)
    private Integer montoEquivalencia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLimiteMax() {
        return limiteMax;
    }

    public void setLimiteMax(Integer limiteMax) {
        this.limiteMax = limiteMax;
    }

    public Integer getLimiteMin() {
        return limiteMin;
    }

    public void setLimiteMin(Integer limiteMin) {
        this.limiteMin = limiteMin;
    }

    public int getMontoEquivalencia() {
        return montoEquivalencia;
    }

    public void setMontoEquivalencia(Integer montoEquivalencia) {
        this.montoEquivalencia = montoEquivalencia;
    }
}
