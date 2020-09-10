package com.github.arquiweb.fidelizacion.rest;

import com.github.arquiweb.fidelizacion.ejb.BolsaPuntosDAO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("bolsa-puntos")
@Consumes("application/json")
@Produces("application/json")
public class BolsaPuntosREST {

    @Inject
    private BolsaPuntosDAO bolsaPuntosDAO;

    @GET
    @Path("/")
    public Response listar(@QueryParam("idCliente") Integer idCliente) {
        return Response.ok(bolsaPuntosDAO.obtenerPorIdCliente(idCliente)).build();
    }

}
