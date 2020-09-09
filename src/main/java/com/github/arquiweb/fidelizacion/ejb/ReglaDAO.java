package com.github.arquiweb.fidelizacion.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.github.arquiweb.fidelizacion.model.Regla;

@Stateless
public class ReglaDAO {
	
	@PersistenceContext(unitName = "fidelizacionPU")
    private EntityManager em;

    public Object obtener() {
        Query q = this.em.createQuery("select p from Regla p");
        List<Regla> reglas = (List<Regla>) q.getResultList();    	
        return reglas;    	
    }  	
	
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
    
    public Integer obtenerEquivalenciaPuntos(Integer montoTotal) throws Exception {
    	Query q = this.em.createQuery("select p from Regla p");
    	List<Regla> reglas = (List<Regla>) q.getResultList();
		for(Regla regla : reglas) {
	    	if(regla.getLimiteMax() == null && regla.getLimiteMin() == null) {
	    		return montoTotal/regla.getMontoEquivalencia();
	    	}
	    	else if(montoTotal <= regla.getLimiteMax() && montoTotal >= regla.getLimiteMin()) {
				return montoTotal/regla.getMontoEquivalencia();
			}
		}
		throw new Exception("Regla inexistente");
    }
}
