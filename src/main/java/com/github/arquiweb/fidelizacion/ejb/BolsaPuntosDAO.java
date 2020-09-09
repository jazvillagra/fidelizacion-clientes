package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.model.BolsaPuntos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class BolsaPuntosDAO {

    @PersistenceContext(unitName = "fidelizacionPU")

    private EntityManager em;

    public void agregar(BolsaPuntos entidad) {
        this.em.persist(entidad);
    }

    public BolsaPuntos obtenerPorId(Integer id){
        return this.em.find(BolsaPuntos.class, id);
    }

    public BolsaPuntos obtenerPorIdCliente(Integer idCliente){
        Query q = this.em.createQuery("select b from BolsaPuntos b where b.idCliente = :param");
        q.setParameter("param", "%"+idCliente+"%");
        return (BolsaPuntos) q.getResultList();
    }

    public void eliminar(Integer id) {
        try {
            BolsaPuntos entity = obtenerPorId(id);
            this.em.remove(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public void actualizar(BolsaPuntos entidad) {
        this.em.merge(entidad);
    }
}
