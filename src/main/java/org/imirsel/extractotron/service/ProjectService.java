package org.imirsel.extractotron.service;




import javax.jws.WebService;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.imirsel.extractotron.dao.hibernate.ProjectNotFoundException;
import org.imirsel.extractotron.model.Project;

import java.util.List;

/**
 * Web Service interface so hierarchy of Generic Manager isn't carried through.
 */
@WebService
@Path("/")
@Produces({"application/json", "application/xml"})
public interface ProjectService {
    @GET
    @Path("/project/get/id={id}")
    Project getProject(@PathParam("id") String projectId);

    @GET
    @Path("/project/all")
    List<Project> getProjects();

    
}
