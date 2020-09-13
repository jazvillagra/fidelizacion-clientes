package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.model.DetCanjePuntos;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class DetCanjePuntosDAO {

    @PersistenceContext(unitName = "fidelizacionPU")
    private EntityManager em;


    public void guardarDetalles(Integer idCanje, List<DetCanjePuntos> detalles){
        for (DetCanjePuntos det : detalles) {
            det.setIdCanje(idCanje);
            this.em.persist(det);
        }
    }
    public List<DetCanjePuntos> obtenerDetallesPorCanje(Integer idCanje){
        Query q = this.em.createQuery("select d from DetCanjePuntos d where d.idCanje = :param");
        q.setParameter("param", idCanje);

        return (List<DetCanjePuntos>) q.getResultList();
    }
}
