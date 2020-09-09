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
    
    public Cliente obtenerCliente(Integer id) {
    	return this.em.find(Cliente.class, id);
    }
    
    public Object obtenerClientesPorParametro(String nombre, String apellido, String fechaNacimiento) {
    	List<Cliente> clientes = null;
        Query q = null;
    	if(nombre != null && !nombre.equals("")) {
    		q = this.em.createQuery("select p from Cliente p where p.nombre like :param");
    		q.setParameter("param", "%"+nombre+"%");
    	} else if(apellido != null && !apellido.equals("")) {
    		q = this.em.createQuery("select p from Cliente p where p.apellido like :param");
    		q.setParameter("param", "%"+apellido+"%");    		
    	} else if(fechaNacimiento != null && !fechaNacimiento.equals("")) {
    		q = this.em.createQuery("select p from Cliente p where to_char(p.fechaNacimiento, 'MM-dd') like :param");
    		q.setParameter("param", fechaNacimiento);    		
    	} else {
            q = this.em.createQuery("select p from Cliente p");
    	}
		clientes = (List<Cliente>) q.getResultList();
    	
        return clientes;    	
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
