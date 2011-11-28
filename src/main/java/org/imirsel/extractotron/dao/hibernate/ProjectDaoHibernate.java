package org.imirsel.extractotron.dao.hibernate;

import java.util.List;

import org.imirsel.extractotron.dao.ProjectDao;
import org.imirsel.extractotron.model.Project;
import org.imirsel.extractotron.model.User;
import org.springframework.stereotype.Repository;

@Repository("projectDao")
public class ProjectDaoHibernate  extends GenericDaoHibernate<Project, Long> implements ProjectDao{

	
	public ProjectDaoHibernate() {
		super(Project.class);
	}

	public List<Project> getProjects() {
        return getHibernateTemplate().find("from Project u order by upper(u.name)");
	}
	
    /**
     * {@inheritDoc}
     */
    public Project saveProject(Project project) {
        if (log.isDebugEnabled()) {
            log.debug("project's id: " + project.getId());
        }
        getHibernateTemplate().saveOrUpdate(project);
        // necessary to throw a DataIntegrityViolation and catch it in ProjectManager
        getHibernateTemplate().flush();
        return project;
    }


}
