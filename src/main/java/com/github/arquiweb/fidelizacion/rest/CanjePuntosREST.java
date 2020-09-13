package com.github.arquiweb.fidelizacion.rest;


import com.github.arquiweb.fidelizacion.ejb.CanjePuntosDAO;
import com.github.arquiweb.fidelizacion.model.CanjePuntos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("canje-puntos")
@Consumes("application/json")
@Produces("application/json")
public class CanjePuntosREST {

    @Inject
    private CanjePuntosDAO canjePuntosDAO;

    @GET
    @Path("/")
    public Response listarPorIdCliente(@QueryParam("idCliente") Integer idCliente){
        return Response.ok(canjePuntosDAO.obtenerCanjePuntosPorId(idCliente)).build();
    }

    @POST
    @Path("/canjear")
    public Response canjearPuntos(CanjePuntos canjePuntos){
        this.canjePuntosDAO.agregarCanjePuntos(canjePuntos);
        return Response.ok().build();
    }
    @GET
    @Path("/listar/concepto")
    public Response listarPorConcepto(@QueryParam("idConcepto") Integer idConcepto){
        return Response.ok(canjePuntosDAO.obtenerCanjesPorConceptoUso(idConcepto)).build();
    }
    @GET
    @Path("/listar/cliente")
    public Response listarPorCliente(@QueryParam("idCliente") Integer idCliente){
        return Response.ok(canjePuntosDAO.obtenerCanjesPorIdCliente(idCliente)).build();
    }
}
