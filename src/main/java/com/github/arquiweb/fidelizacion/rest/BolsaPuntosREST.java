package com.github.arquiweb.fidelizacion.rest;

import com.github.arquiweb.fidelizacion.ejb.BolsaPuntosDAO;
import com.github.arquiweb.fidelizacion.ejb.ClienteDAO;
import com.github.arquiweb.fidelizacion.model.Cliente;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("bolsa-puntos")
@Consumes("application/json")
@Produces("application/json")
public class BolsaPuntosREST {

    @Inject
    private BolsaPuntosDAO bolsaPuntosDAO;

    @POST
    @Path("/")
    public Response agregarPuntos(@QueryParam("idCliente") Integer idCliente, 
    		@QueryParam("monto") Integer monto) throws Exception {
    	this.bolsaPuntosDAO.calcBolsaPuntos(idCliente, monto);
    	return Response.ok().build();
    }
     
}
