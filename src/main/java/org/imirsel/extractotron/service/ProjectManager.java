package org.imirsel.extractotron.service;

import java.util.List;

import org.imirsel.extractotron.dao.ProjectDao;
import org.imirsel.extractotron.model.Project;

public interface ProjectManager extends GenericManager<Project, Long>{
	  void setProjectDao(ProjectDao projectDao);
	  List<Project> search(String searchTerm);
}
