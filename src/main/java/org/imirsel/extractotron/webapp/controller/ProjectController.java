package org.imirsel.extractotron.webapp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.model.Project;
import org.imirsel.extractotron.model.SongCollection;
import org.imirsel.extractotron.model.Workspace;
import org.imirsel.extractotron.service.ExecutionContextManager;
import org.imirsel.extractotron.service.ExecutionManager;
import org.imirsel.extractotron.service.ProjectManager;
import org.imirsel.extractotron.service.UserManager;
import org.imirsel.extractotron.service.WorkspaceManager;
import org.imirsel.extractotron.service.impl.executor.RemoteProcess;
import org.imirsel.extractotron.service.impl.executor.RemoteProcessMonitor;
import org.imirsel.extractotron.service.impl.executor.RemoteProcessMonitorImpl;
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
	private ExecutionContextManager executionContextManager;
	@Autowired
	private ExecutionManager executionManager;
	
	@Autowired
	private WorkspaceManager workspaceManager;

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
		ExecutionContext ec= executionContextManager.get(new Long(ec_id));
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
			
			if(ec.getStatus().equalsIgnoreCase(Constants.RUNNING)){
				message = message  + "<br/>" + "Please abort the executor before deleting it.";
			}else if(ec.getStatus().equalsIgnoreCase(Constants.ABORT_REQUESTED)){
				message = message  + "<br/>" + "Abort has been requested. Please wait for the abort to complete.";
			}
			// cannot be stopped not in the correct status
		}
	   System.out.println("==> " + message);
	   project =userManager.getProjectCurrentUser(new Long(project_id));
	   return new ModelAndView("project/projectDetail", Constants.PROJECT, project);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/executionDetail")
	public ModelAndView executionDetail(@RequestParam(required = true, value = "id")
			String uuid, @RequestParam(required = true, value = "project_id") String project_id,
			HttpServletRequest request) throws Exception {

		//RemoteProcess remoteProcess = executionManager.getRemoteProcess(uuid);
		ExecutionContext executionContext = executionContextManager.getExecutionContextFromUuid(uuid);
		Project project =userManager.getProjectCurrentUser(new Long(project_id));
		
	     Map map = new HashMap();
		 map.put(Constants.PROJECT, project);
		 //map.put(Constants.REMOTE_PROCESS,remoteProcess);
		 map.put(Constants.EXECUTION_CONTEXT, executionContext);
		 request.setAttribute("map",map);
		 return new ModelAndView("/execution/executionDetail",Constants.MAP,map);
	}
	

	@RequestMapping(method = RequestMethod.GET, value="/execute")
	public ModelAndView execute(@RequestParam(required = true, value = "id") String project_id,
			HttpServletRequest request) throws Exception {
	
		// validate the commandline -make sure the ${feature} is selected
		Project project =userManager.getProjectCurrentUser(new Long(project_id));
		String commandLine=project.getCommandLine();
		String feature=project.getFeature();
		System.out.println("FEATURE: " + feature);
		System.out.println("COMMAND LINE: " + commandLine);
		Set<SongCollection> collections=project.getSongCollections();
		System.out.println("Song Collections: "+ collections.size());
		
		UUID uid = UUID.randomUUID();
		// create a workspace directory -create an input file with the list of songs
		Workspace workspace=workspaceManager.getWorkspaceForProject(project.getId(), uid.toString());
	
		
		if(commandLine.contains("${feature}")){
			// error
		}
		// validate that the  collection(s) are selected.
		if(collections.size()==0){
			// error
		}else{
			String inputFile=workspaceManager.createInputFile(workspace,collections);
		}
		
		
		
		// parse the commandline to replace the variables -the input file and the output result
		// file
		commandLine=commandLine.replaceAll(Pattern.quote("${inputFileWithFileList}"), workspace.getInputFile());
		commandLine=commandLine.replaceAll(Pattern.quote("${outputFile}"), workspace.getOutputFile());
		// copy the commandline from this project to the execution context
		project.setCommandLine(commandLine);
		
		// create a new execution context
		ExecutionContext ec = new ExecutionContext();
		ec.setCommandLine(commandLine);
		ec.setName("EC name: " + System.currentTimeMillis());
		ec.setTimeCreated(new Date());
		ec.setResultFile(workspace.getOutputFile());
		ec.setInputFile(workspace.getInputFile());
		ec.setWorkingDirectory(workspace.getDirectory());
		ec.setErrorLogFile(workspace.getErrFile());
		ec.setOutputFile(workspace.getOutputFile());
		
		ec.setPid(-1l);
		
		// set the status of  the ec to created.
		ec.setStatus(Constants.CREATED);
		ec.setUuid(uid.toString());
		ec=executionContextManager.save(ec);
		
		// add the EC to the project and and save the project
		project.addExecutionContext(ec);
		projectManager.saveProject(project);
		
		// Start the execution engine -which will return the PID of the process.
		RemoteProcessMonitor rpm = new RemoteProcessMonitorImpl(executionContextManager);
		RemoteProcess remoteProcess=executionManager.execute(ec,rpm);
		
		
		
		// make sure the timer task is running that will look at the database for EC rows with "CREATED"
		// and the PID -see if the process is running or not and update the database entry subsequently.
	     //Map map = new HashMap();
		// map.put(Constants.PROJECT, project);
		 //map.put(Constants.REMOTE_PROCESS,remoteProcess);
		 //request.setAttribute("map",map);
		
		 return new ModelAndView("redirect:/project/executionDetail?id="+remoteProcess.getExecutionContext().getUuid()+"&project_id="+project.getId());
	}
			
	

}
