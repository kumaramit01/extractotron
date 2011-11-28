package org.imirsel.extractotron.service.impl;


import org.imirsel.extractotron.dao.ProjectDao;
import org.imirsel.extractotron.dao.hibernate.ProjectNotFoundException;
import org.imirsel.extractotron.model.Project;
import org.imirsel.extractotron.service.ProjectManager;
import org.imirsel.extractotron.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;


/**
 * Implementation of ProjectManager interface.
 *
 */
@Service("projectManager")
@WebService(serviceName = "ProjectService", endpointInterface = "org.imirsel.extractotron.service.ProjectService")
public class ProjectManagerImpl extends GenericManagerImpl<Project, Long> implements ProjectManager, ProjectService {

	private ProjectDao projectDao;

	@Autowired
    public void setProjectDao(ProjectDao projectDao) {
		this.dao = projectDao;
        this.projectDao = projectDao;
    }

    /**
     * {@inheritDoc}
     */
    public Project getProject(String id) {
        return projectDao.get(new Long(id));
    }

    /**
     * {@inheritDoc}
     */
    public List<Project> getProjects() {
        return projectDao.getAllDistinct();
    }

    
    /**
     * {@inheritDoc}
     */
    public List<Project> search(String searchTerm) {
        return super.search(searchTerm, Project.class);
    }

    


	
}
