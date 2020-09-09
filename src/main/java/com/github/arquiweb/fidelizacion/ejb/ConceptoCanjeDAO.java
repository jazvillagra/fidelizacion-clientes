package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.model.Cliente;
import com.github.arquiweb.fidelizacion.model.ConceptoCanje;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Stateless
public class ConceptoCanjeDAO {

    @PersistenceContext(unitName = "fidelizacionPU")

    private EntityManager em;

    public void agregar(ConceptoCanje entidad) {this.em.persist(entidad);}

    public ConceptoCanje obtenerConceptoCanje(Integer id) {return this.em.find(ConceptoCanje.class, id);}

    public Object obtenerClientesPorParametro() {
        List<ConceptoCanje> conceptosCanje = null;
        Query q = null;

        q = this.em.createQuery("select p from ConceptoCanje p");

        conceptosCanje = (List<ConceptoCanje>) q.getResultList();

        return conceptosCanje;
    }
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
