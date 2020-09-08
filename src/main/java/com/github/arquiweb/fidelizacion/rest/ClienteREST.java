package com.github.arquiweb.fidelizacion.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.github.arquiweb.fidelizacion.ejb.ClienteDAO;
import com.github.arquiweb.fidelizacion.model.Cliente;

@Path("cliente")
@Consumes("application/json")
@Produces("application/json")
public class ClienteREST {

    @Inject
    private ClienteDAO clienteDAO;

    @GET
    @Path("/")
    public Response listar() {
        return Response.ok(clienteDAO.lista()).build();
    }

    @POST
    @Path("/")
    public Response crear(Cliente p) {
        this.clienteDAO.agregar(p);
        return Response.ok().build();

    }
}
