package org.imirsel.extractotron.webapp.controller;

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
@RequestMapping("/project/*")
public class ProjectController {
	@Autowired
	private ProjectManager projectManager;
	@Autowired
	private UserManager userManager;
	@Autowired
	private ExecutionContextManager executionManager;

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

	@RequestMapping(method = RequestMethod.GET, value="/remove_executor")
	public ModelAndView removeExecutor(@RequestParam(required = true, value = "project_id") String project_id,
			@RequestParam(required = true, value = "ec_id") String ec_id) throws Exception {
		// get the project to make sure it is owned by the current user
		Project project =userManager.getProjectCurrentUser(new Long(project_id));
		ExecutionContext ec= executionManager.get(new Long(ec_id));
		boolean success = false;
		String message = "";
		if(ec.canBeStopped()){
			 success=project.getExecutionContexts().remove(ec);
			 if(success)
			 message ="Executor " + ec.getName() + " successfully removed.";
			 else
			 message ="Executor " + ec.getName() + " id = " + ec.getId() + 
			 "  not found in the project " + project.getName() + "  project id = " + project.getId();
			 userManager.saveProject(project);
		}else{
			message  = "Executor " + ec.getName() + " could not be removed. Status: " + ec.getStatus(); 
			
			if(ec.getStatus().equalsIgnoreCase("RUNNING")){
				message = message  + "<br/>" + "Please abort the executor before deleting it.";
			}else if(ec.getStatus().equalsIgnoreCase("ABORT_REQUESTED")){
				message = message  + "<br/>" + "Abort has been requested. Please wait for the abort to complete.";
			}
			// cannot be stopped not in the correct status
		}
	   System.out.println("==> " + message);
	   project =userManager.getProjectCurrentUser(new Long(project_id));
	   return new ModelAndView("project/projectDetail", Constants.PROJECT, project);
	}

	@RequestMapping(method = RequestMethod.GET, value="/execute")
	public ModelAndView execute(@RequestParam(required = true, value = "id") String project_id) throws Exception {
		// validate the commandline -make sure the ${feature} is selected
		// validate that the  collection(s) are selected.
		// create a workspace directory -create an input file with the list of songs
		// parse the commandline to replace the variables -the input file and the output result
		// file
		// create a new execution context
		// copy the commandline from this project to the execution context
		// set the status of  the ec to created.
		// add the EC to the project and and save the project
		// Start the execution engine -which will return the PID of the process.
		// update the EC with the PID.
		// make sure the timer task is running that will look at the database for EC rows with "STARTED"
		// and the PID -see if the process is running or not and update the database entry subsequently.
		
		
		return null;
	}
			
	

}
