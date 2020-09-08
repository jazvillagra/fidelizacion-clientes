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
}
