package org.imirsel.extractotron.service;

import org.imirsel.extractotron.dao.hibernate.CollectionNotFoundException;
import org.imirsel.extractotron.model.SongCollection;


import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * Web Service interface so hierarchy of Generic Manager isn't carried through.
 */
@WebService
@Path("/")
@Produces({"application/json", "application/xml"})
public interface CollectionService {
    @GET
    @Path("/collection/id")
    SongCollection getCollection(@PathParam("id") String collectionId);

    @GET
    @Path("/collection/name/{name}")
    SongCollection getCollectionByName(@PathParam("name") String name) throws CollectionNotFoundException;

    @GET
    @Path("/collection/all")
    List<SongCollection> getCollections();

    
}
