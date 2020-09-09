package com.github.arquiweb.fidelizacion.rest;

import com.github.arquiweb.fidelizacion.ejb.ClienteDAO;
import com.github.arquiweb.fidelizacion.model.Cliente;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("cliente")
@Consumes("application/json")
@Produces("application/json")
public class ClienteREST {

    @Inject
    private ClienteDAO clienteDAO;

    @GET
    @Path("/")
    public Response listar(@QueryParam("nombre") String nombre, 
    		@QueryParam("apellido") String apellido, 
    		@QueryParam("fechaNacimiento") String fechaNacimiento) {
        return Response.ok(clienteDAO.obtenerClientesPorParametro(nombre, apellido, fechaNacimiento)).build();
    }

    @POST
    @Path("/")
    public Response crear(Cliente cliente) {
        this.clienteDAO.agregar(cliente);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam(value = "id") Integer id) {
    	try {
	        this.clienteDAO.eliminar(id);
	        return Response.ok().build();
    	}catch (Exception e) {
    		return Response.serverError().build();
		}
    }
    
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam(value = "id") Integer id, Cliente cliente) {
    	cliente.setId(id);
        this.clienteDAO.actualizar(cliente);
        return Response.ok().build();
    }   
}
