package org.imirsel.extractotron.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.imirsel.extractotron.model.ExecutionContext;


/**
 * Web Service interface so hierarchy of Generic Manager isn't carried through.
 */
@WebService
@Path("/")
@Produces({"application/json", "application/xml"})
public interface ExecutionContextService {
    @GET
    @Path("/executioncontext/get/id={id}")
    ExecutionContext getExecutionContext(@PathParam("id") String executionContextId);

    @GET
    @Path("/executioncontext/all")
    List<ExecutionContext> getExecutionContexts();

}
