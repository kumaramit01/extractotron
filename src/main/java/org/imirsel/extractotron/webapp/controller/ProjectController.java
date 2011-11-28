package org.imirsel.extractotron.webapp.controller;

import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.service.ProjectManager;
import org.imirsel.extractotron.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project/*")
public class ProjectController {
	private ProjectManager projectManager;
	private UserManager userManager;
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Autowired
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	@RequestMapping(method = RequestMethod.GET, value="/search")
	public ModelAndView search(@RequestParam(required = false, value = "q") String query) throws Exception {
	   return new ModelAndView("project/projectList", Constants.PROJECT_LIST, projectManager.search(query));
	}
	  
	@RequestMapping(method = RequestMethod.GET, value="/list")
	public ModelAndView list() throws Exception {
	  return new ModelAndView("project/projectList", Constants.PROJECT_LIST, userManager.getProjectsCurrentUser());
	}

	@RequestMapping(method = RequestMethod.GET, value="/get")
	public ModelAndView get(@RequestParam(required = true, value = "id") String id) throws Exception {
	  return new ModelAndView("project/projectDetail", Constants.PROJECT, userManager.getProjectCurrentUser(new Long(id)));
	}

}
