package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.mail.SimpleEmail;
import com.github.arquiweb.fidelizacion.model.BolsaPuntos;
import com.github.arquiweb.fidelizacion.model.CanjePuntos;
import com.github.arquiweb.fidelizacion.model.ConceptoCanje;
import com.github.arquiweb.fidelizacion.model.DetCanjePuntos;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class CanjePuntosDAO {

    @PersistenceContext(unitName = "fidelizacionPU")
    private EntityManager em;

    @Inject
    private BolsaPuntosDAO bolsaPuntosDAO;

    @Inject
    private DetCanjePuntosDAO detCanjePuntosDAO;

    @Inject
    private ClienteDAO clienteDAO;

    @Inject
    private ConceptoCanjeDAO conceptoCanjeDAO;

    private SimpleEmail mailSender;

    public Object obtenerCanjePuntosPorId(Integer id){
        return this.em.find(CanjePuntos.class, id);
    }
    /*
     * Para hacer el canje de puntos de un cliente, se deben sustraer los puntos de las bolsas del cliente cuya
     * fecha de caducidad no haya pasado y cuyo saldo de puntos sea mayor a cero.
     * Se deben restar primero los puntos de las bolsas más viejas (FIFO)
     * Se envia un email de confirmacion al cliente cuando se persisten los datos
     */
    public void agregarCanjePuntos(CanjePuntos canje){

        List<BolsaPuntos> bolsasCliente = bolsaPuntosDAO
                .obtenerPorIdClienteConFechasAscendentes(canje.getIdCliente());
        List<DetCanjePuntos> detalles = new ArrayList<>();
        int puntajeAUtilizar = canje.getPuntajeUtilizado();

        for (BolsaPuntos bolsa: bolsasCliente) {
            while (puntajeAUtilizar > 0) {
                DetCanjePuntos detalleCanje = new DetCanjePuntos();
                if (bolsa.getFechaVencimiento().after(new Date()) && bolsa.getSaldo() > 0) {
                    if (bolsa.getSaldo() <= puntajeAUtilizar) {
                        puntajeAUtilizar = puntajeAUtilizar - bolsa.getSaldo();
                        detalleCanje.setPuntajeUtilizado(bolsa.getSaldo());
                        detalleCanje.setIdBolsaPuntos(bolsa.getId());
                        bolsa.setSaldo(0);
                    } else if (bolsa.getSaldo() > puntajeAUtilizar) {
                        int saldoBolsa = bolsa.getSaldo() - puntajeAUtilizar;
                        bolsa.setSaldo(saldoBolsa);
                        puntajeAUtilizar = 0;
                    }
                }
                detalles.add(detalleCanje);
            }
            bolsaPuntosDAO.actualizar(bolsa);
        }
        this.em.persist(canje);
        this.em.flush();
        detCanjePuntosDAO.guardarDetalles(canje.getId(), detalles);

        try {
            mailSender.sendEmailConfirmacionCanje(clienteDAO.obtenerCliente(canje.getIdCliente()), canje.getPuntajeUtilizado(),
                    conceptoCanjeDAO.obtenerConceptoCanje(canje.getId()).getDescConcepto(), canje.getFechaUso());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public List<CanjePuntos> obtenerCanjesPorConceptoUso(Integer idConcepto){

        Query q = this.em.createQuery("select c from CanjePuntos c where c.idConcepto = :param");
        q.setParameter("param", idConcepto);

        return (List<CanjePuntos>)q.getResultList();
    }

    public List<CanjePuntos> obtenerCanjesPorIdCliente(Integer idCliente){

        Query q = this.em.createQuery("select c from CanjePuntos c where c.idCliente = :param");
        q.setParameter("param", idCliente);

        return (List<CanjePuntos>)q.getResultList();
    }
    public List<CanjePuntos> obtenerCanjesPorFechaCanje(String fechaCanje){

        Query q = this.em.createQuery("select c from CanjePuntos c where to_char(c.fechaUso, 'YYYY-MM-dd') like :param");
        q.setParameter("param", fechaCanje);

        return (List<CanjePuntos>)q.getResultList();
    }
}
