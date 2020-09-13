package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.model.VencimientoPuntos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    

    public Date calculaFechaVencimiento(Date today) throws Exception{
    	Query q = this.em.createQuery("select b from VencimientoPuntos b");
    	List<VencimientoPuntos> vencimientos = q.getResultList();
    	for(VencimientoPuntos venc : vencimientos) {
    		if(venc.getFechaIniValidez().before(today) && venc.getFechaFinValidez().after(today)) {
    			Calendar c = Calendar.getInstance(); 
    			c.setTime(today); 
    			c.add(Calendar.DATE, venc.getDuracion());
    			return c.getTime();
    		}
    	}
    	throw new Exception("No hay fecha de vencimiento disponible");   	
    }
}
