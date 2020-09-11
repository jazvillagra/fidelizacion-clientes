package com.github.arquiweb.fidelizacion.rest;

import com.github.arquiweb.fidelizacion.ejb.BolsaPuntosDAO;
import com.github.arquiweb.fidelizacion.model.BolsaPuntos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("bolsa-puntos")
@Consumes("application/json")
@Produces("application/json")
public class BolsaPuntosREST {

    @Inject
    private BolsaPuntosDAO bolsaPuntosDAO;

    @GET
    @Path("/")
    public Response listar( @QueryParam("idCliente") Integer idCliente,
                            @QueryParam("rangoInicio") Integer rangoInicio,
                            @QueryParam("rangoFin") Integer rangoFin ) throws Exception {
        if (idCliente != null) {
            return Response.ok(bolsaPuntosDAO.obtenerPorIdCliente(idCliente)).build();
        }else if (rangoFin != null && rangoInicio != null){
            return  Response.ok(bolsaPuntosDAO.obtenerPorRangoPuntos(rangoInicio, rangoFin)).build();
        } else {
            return  Response.ok("Envie algun parametro(idCliente,rangoInicio and rangoFin)").build();
        }
    }

}
