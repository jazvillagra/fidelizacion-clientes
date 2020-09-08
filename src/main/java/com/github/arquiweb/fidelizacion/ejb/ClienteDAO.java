package com.github.arquiweb.fidelizacion.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.github.arquiweb.fidelizacion.model.Cliente;

@Stateless
public class ClienteDAO {

	@PersistenceContext(unitName = "fidelizacionPU")
	
    private EntityManager em;

    public void agregar(Cliente entidad) {
        this.em.persist(entidad);
    }

    public Object lista() {
        Query q = this.em.createQuery("select p from Cliente p");
        List<Cliente> clientes = (List<Cliente>) q.getResultList();
        return clientes;
    }

    public Cliente obtenerCliente(Integer id) {
    	return this.em.find(Cliente.class, id);
    }
    
	public void eliminar(Integer id) {
		try {
			Cliente entity = obtenerCliente(id);
			this.em.remove(entity);
		} catch (Exception e) {
			throw e;
		}
	}
	
    public void actualizar(Cliente entidad) {
        this.em.merge(entidad);
    }
}
