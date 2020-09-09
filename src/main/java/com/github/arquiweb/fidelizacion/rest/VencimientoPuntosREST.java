package com.github.arquiweb.fidelizacion.rest;

import com.github.arquiweb.fidelizacion.ejb.VencimientoPuntosDAO;
import com.github.arquiweb.fidelizacion.model.VencimientoPuntos;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("vencimiento-puntos")
@Consumes("application/json")
@Produces("application/json")
public class VencimientoPuntosREST {

    @Inject
    private VencimientoPuntosDAO vencimientoPuntosDAO;


    @GET
    @Path("/listar/{id}")
    public Response obtenerVencimientoPuntosPorId(@PathParam("id") Integer id) {
        return Response.ok(vencimientoPuntosDAO.obtenerVencimiento(id)).build();
    }

    @POST
    @Path("/")
    public Response crear(VencimientoPuntos vencimientoPuntos) {
        this.vencimientoPuntosDAO.agregar(vencimientoPuntos);
        return Response.ok().build();
    }
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam(value = "id") Integer id) {
        try {
            this.vencimientoPuntosDAO.eliminar(id);
            return Response.ok().build();
        }catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam(value = "id") Integer id, VencimientoPuntos vencimientoPuntos) {
        vencimientoPuntos.setId(id);
        this.vencimientoPuntosDAO.actualizar(vencimientoPuntos);
        return Response.ok().build();
    }
}
