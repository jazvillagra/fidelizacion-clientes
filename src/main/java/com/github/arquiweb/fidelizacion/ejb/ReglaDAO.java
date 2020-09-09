package com.github.arquiweb.fidelizacion.ejb;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.arquiweb.fidelizacion.model.Cliente;
import com.github.arquiweb.fidelizacion.model.Regla;


public class ReglaDAO {
	
	@PersistenceContext(unitName = "fidelizacionPU")
	
    private EntityManager em;
	
    public void agregar(Regla entidad) {
        this.em.persist(entidad);
    }
    
    public Regla obtenerRegla(Integer id) {
    	return this.em.find(Regla.class, id);
    }    
    
	public void eliminar(Integer id) {
		try {
			Regla entity = obtenerRegla(id);
			this.em.remove(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
    public void actualizar(Regla entidad) {
        this.em.merge(entidad);
    }
}
