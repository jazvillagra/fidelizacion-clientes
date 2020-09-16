package com.github.arquiweb.fidelizacion.ejb;

import com.github.arquiweb.fidelizacion.model.*;
import com.github.arquiweb.fidelizacion.utils.EmailUtils;

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

    private final EmailUtils mailSender = new EmailUtils();

    public List<CanjePuntos> obtenerCanjePuntos(){

        Query q = this.em.createQuery("select c from CanjePuntos c");

        return (List<CanjePuntos>)q.getResultList();
    }
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
        for (int i = 0 ; i <= bolsasCliente.size() ; i++) {
            DetCanjePuntos detalleCanje = new DetCanjePuntos();
            if (bolsasCliente.get(i).getFechaVencimiento().after(new Date())
                    && bolsasCliente.get(i).getSaldo() > 0) {
                if (bolsasCliente.get(i).getSaldo() <= puntajeAUtilizar) {
                    puntajeAUtilizar = puntajeAUtilizar - bolsasCliente.get(i).getSaldo();
                    bolsasCliente.get(i).setPuntajeUtilizado(bolsasCliente.get(i).getSaldo());
                    bolsasCliente.get(i).setSaldo(0);
                    detalleCanje.setIdBolsaPuntos(bolsasCliente.get(i).getId());
                    detalleCanje.setPuntajeUtilizado(bolsasCliente.get(i).getPuntajeUtilizado());
                }
                if (bolsasCliente.get(i).getSaldo() > puntajeAUtilizar && puntajeAUtilizar > 0) {
                    bolsasCliente.get(i).setPuntajeUtilizado(puntajeAUtilizar);
                    bolsasCliente.get(i).setSaldo(bolsasCliente.get(i).getSaldo() - puntajeAUtilizar);
                    detalleCanje.setIdBolsaPuntos(bolsasCliente.get(i).getId());
                    detalleCanje.setPuntajeUtilizado(bolsasCliente.get(i).getPuntajeUtilizado());
                    puntajeAUtilizar = 0;
                }
                if(detalleCanje.getPuntajeUtilizado()!= 0){
                    detalles.add(detalleCanje);
                }
            }
            bolsaPuntosDAO.actualizar(bolsasCliente.get(i));
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
