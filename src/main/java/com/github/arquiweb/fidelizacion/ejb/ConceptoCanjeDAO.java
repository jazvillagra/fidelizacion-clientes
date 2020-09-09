package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.model.ConceptoCanje;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class ConceptoCanjeDAO {

    @PersistenceContext(unitName = "fidelizacionPU")

    private EntityManager em;

    public void agregar(ConceptoCanje entidad) {this.em.persist(entidad);}

    public ConceptoCanje obtenerConceptoCanje(Integer id) {return this.em.find(ConceptoCanje.class, id);}

    public void eliminar(Integer id) {
        try {
            ConceptoCanje entity = obtenerConceptoCanje(id);
            this.em.remove(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public void actualizar(ConceptoCanje entidad) {
        this.em.merge(entidad);
    }
}
