package com.github.arquiweb.fidelizacion.rest;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.github.arquiweb.fidelizacion.ejb.ClienteDAO;
import com.github.arquiweb.fidelizacion.ejb.ReglaDAO;
import com.github.arquiweb.fidelizacion.model.Cliente;
import com.github.arquiweb.fidelizacion.model.Regla;

@Path("cliente")
@Consumes("application/json")
@Produces("application/json")
public class ReglaREST {

    @Inject
    private ReglaDAO reglaDAO;

    @GET
    @Path("/")
    public Response listar() {
        return Response.ok(reglaDAO.obtener()).build();
    }

    @POST
    @Path("/")
    public Response crear(Regla cliente) {
        this.reglaDAO.agregar(cliente);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam(value = "id") Integer id) {
    	try {
	        this.reglaDAO.eliminar(id);
	        return Response.ok().build();
    	}catch (Exception e) {
    		return Response.serverError().build();
		}
    }
    
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam(value = "id") Integer id, Regla cliente) {
    	cliente.setId(id);
        this.reglaDAO.actualizar(cliente);
        return Response.ok().build();
    }   
}
