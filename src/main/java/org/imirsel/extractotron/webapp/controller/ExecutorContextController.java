package org.imirsel.extractotron.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.model.Project;
import org.imirsel.extractotron.service.ExecutionContextManager;
import org.imirsel.extractotron.service.ProjectManager;
import org.imirsel.extractotron.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/executor/*")
public class ExecutorContextController {
	@Autowired
	private UserManager userManager;
	@Autowired
	private ExecutionContextManager executionContextManager;
	
	  

	@RequestMapping(method = RequestMethod.GET, value="/list")
	public ModelAndView list(@RequestParam(required = true, value = "id") String id) throws Exception {
      Project project = userManager.getProjectCurrentUser(new Long(id));
      Map map = new HashMap();
	  map.put(Constants.EC_LIST, project.getExecutionContexts());
	  map.put("project",project);
	  return new ModelAndView("executor/executorList", Constants.MAP, map );
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/get")
	public ModelAndView get(@RequestParam(required = true, value = "project_id") String project_id,
			@RequestParam(required = true, value = "ec_id") String ec_id, HttpServletResponse response ) throws Exception {
      Project project = userManager.getProjectCurrentUser(new Long(project_id));
      ExecutionContext ec = executionContextManager.get(new Long (ec_id));
      if(project.getExecutionContexts().contains(ec)){
    	  Map map = new HashMap();
    	  map.put(Constants.EC, ec);
    	  map.put("project",project);
    	  return new ModelAndView("executor/executorDetail", Constants.MAP, map);
      }else{
    	  // not found
    	  response.sendError(404);
      }
	return null;
	}

}
