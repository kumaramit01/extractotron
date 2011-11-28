package org.imirsel.extractotron.dao;

import java.util.List;

import org.imirsel.extractotron.dao.hibernate.ProjectNotFoundException;
import org.imirsel.extractotron.model.Project;


public interface ProjectDao extends GenericDao<Project, Long>  {

	 /**
     * Gets a list of collections ordered by the uppercase version of their name.
     *
     * @return List populated list of song collection
     */
    List<Project> getProjects();
    public Project saveProject(Project project);
    
	//Project loadProjectByName(String name) throws ProjectNotFoundException;

}
