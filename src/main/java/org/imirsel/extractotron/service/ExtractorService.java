package org.imirsel.extractotron.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.imirsel.extractotron.model.Extractor;
import org.imirsel.extractotron.model.Project;

/**
 * Web Service interface so hierarchy of Generic Manager isn't carried through.
 */
@WebService
@Path("/")
@Produces({"application/json", "application/xml"})
public interface ExtractorService {
    @GET
    @Path("/project/get/id={id}")
    Extractor getExtractor(@PathParam("id") String extractorId);

    @GET
    @Path("/project/all")
    List<Extractor> getExtractors();

}
