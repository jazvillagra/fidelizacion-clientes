package com.github.arquiweb.fidelizacion.rest;


import com.github.arquiweb.fidelizacion.ejb.ConceptoCanjeDAO;
import com.github.arquiweb.fidelizacion.model.ConceptoCanje;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("conceptoCanje")
@Consumes("application/json")
@Produces("application/json")
public class ConceptoCanjeREST {

    @Inject
    private ConceptoCanjeDAO conceptoCanjeDao;

    /*@GET
    @Path("/")
    public response lista(@QueryParam("desc_concepto") Integer descConcepto)

     */

    @POST
    @Path("/")
    public Response crear(ConceptoCanje conceptoCanje) {
        this.conceptoCanjeDao.agregar(conceptoCanje);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam(value = "id") Integer id){
        try {
            this.conceptoCanjeDao.eliminar(id);
            return Response.ok().build();
        }catch (Exception a) {
            return Response.serverError().build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam(value = "id") Integer id, ConceptoCanje conceptoCanje) {
        conceptoCanje.setId(id);
        this.conceptoCanjeDao.actualizar(conceptoCanje);
        return Response.ok().build();
    }

}
