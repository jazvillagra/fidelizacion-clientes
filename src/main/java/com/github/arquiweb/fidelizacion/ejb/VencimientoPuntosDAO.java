package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.model.VencimientoPuntos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class VencimientoPuntosDAO {

    @PersistenceContext(unitName = "fidelizacionPU")

    private EntityManager em;

    public void agregar(VencimientoPuntos entidad) {
        this.em.persist(entidad);
    }

    public VencimientoPuntos obtenerVencimiento(Integer idVencimiento){
        return this.em.find(VencimientoPuntos.class, idVencimiento);
    }

    public void eliminar(Integer id) {
        try {
            VencimientoPuntos entity = obtenerVencimiento(id);
            this.em.remove(entity);
        } catch (Exception e) {
            throw e;
        }
    }
    public void actualizar(VencimientoPuntos entidad) {
        this.em.merge(entidad);
    }
}
