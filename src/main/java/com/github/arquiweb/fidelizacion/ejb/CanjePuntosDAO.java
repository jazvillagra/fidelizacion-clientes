package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.utils.EmailUtils;
import com.github.arquiweb.fidelizacion.model.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.*;

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

    private final EmailUtils mailSender = new EmailUtils();

    public Object obtenerCanjePuntosPorId(Integer id){
        return this.em.find(CanjePuntos.class, id);
    }
    /*
     * Para hacer el canje de puntos de un cliente, se deben sustraer los puntos de las bolsas del cliente
     * cuya fecha de caducidad no haya pasado y cuyo saldo de puntos sea mayor a cero.
     * Se deben restar primero los puntos de las bolsas más viejas (FIFO)
     * Se envia un email de confirmacion al cliente cuando se persisten los datos
     */
    public void agregarCanjePuntos(Integer idCliente, Integer idConceptoCanje) throws Exception {

        Cliente cliente = clienteDAO.obtenerCliente(idCliente);
        ConceptoCanje conceptoCanje = conceptoCanjeDAO.obtenerConceptoCanje(idConceptoCanje);
        List<BolsaPuntos> bolsasCliente = bolsaPuntosDAO.obtenerPorIdCliente(idCliente);
        List<DetCanjePuntos> detalles = new ArrayList<>();

        if (conceptoCanje != null) {
            int puntajeAUtilizar = conceptoCanje.getPuntosRequeridos();

            if (Boolean.TRUE.equals(clienteHabilitadoParaCanje(bolsasCliente, puntajeAUtilizar))
                    && !bolsasCliente.isEmpty()) {

                CanjePuntos canje = new CanjePuntos();

                canje.setIdCliente(idCliente);
                canje.setIdConcepto(idConceptoCanje);

                canjearPuntosDeBolsas(bolsasCliente, puntajeAUtilizar, detalles);

                canje.setPuntajeUtilizado(conceptoCanje.getPuntosRequeridos());
                canje.setFechaUso(new Date());

                this.em.persist(canje);
                this.em.flush();

                detCanjePuntosDAO.guardarDetalles(canje.getId(), detalles);

                try {
                    mailSender.sendEmailConfirmacionCanje(cliente, canje.getPuntajeUtilizado(),
                            conceptoCanje.getDescConcepto(), canje.getFechaUso());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else {
                throw new Exception("No tiene suficientes puntos para realizar el canje");
            }
        } else{
            throw new Exception("No existe el concepto con id: "+ idConceptoCanje);
        }
    }

    private Boolean clienteHabilitadoParaCanje(List<BolsaPuntos> bolsasCliente, Integer puntajeAUtilizar) {
        int totalPuntos = 0;
        for (BolsaPuntos bolsa : bolsasCliente) {
            if(bolsa.getFechaVencimiento().after(new Date())) {
                totalPuntos = totalPuntos + bolsa.getSaldo();
            }
        }
        if (totalPuntos >= puntajeAUtilizar){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    private List<DetCanjePuntos> canjearPuntosDeBolsas(List<BolsaPuntos> bolsasCliente, Integer puntajeAUtilizar, List<DetCanjePuntos> detalles){
        for (int i = bolsasCliente.size() - 1 ; i >= 0 ; i--) {
            BolsaPuntos bolsa = bolsasCliente.get(i);
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
        return detalles;
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
