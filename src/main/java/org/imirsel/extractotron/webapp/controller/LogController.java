package org.imirsel.extractotron.webapp.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.model.Workspace;
import org.imirsel.extractotron.service.ExecutionContextManager;
import org.imirsel.extractotron.service.WorkspaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/log")
public class LogController {
	
	@Autowired
	private ExecutionContextManager executionContextManager;
	
	@Autowired
	private WorkspaceManager workspaceManager;

	

	@RequestMapping(method = RequestMethod.GET, value="/project/{projectId}/id/{uuid}")
	public ModelAndView id(@PathVariable Long projectId,@PathVariable String uuid, 
			HttpServletResponse response, HttpServletRequest request) throws IOException{
		ExecutionContext ec= null;
		try{
			 ec=executionContextManager.getExecutionContextFromUuid(uuid);
		}catch(Exception ex){
			response.sendError(404);
			return null;
		}
		
		Workspace workspace=workspaceManager.getWorkspaceForProject(projectId, uuid);
		
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append("<html><head><title>"+ec.getName()+"</title></head><body>");
		sbuilder.append("<table><tr>File Name<td></td><td>Size</td></tr>");
		sbuilder.append(getFileInfo(workspace.getInputFile(), workspace.getDirectory()));
		sbuilder.append(getFileInfo(workspace.getOutputFile(),workspace.getDirectory()));
		sbuilder.append(getFileInfo(workspace.getErrFile(), workspace.getDirectory()));
		sbuilder.append("</table>");
		sbuilder.append("</body></html>");
		
		response.setContentType("text/html");
		response.getWriter().print(sbuilder.toString());
		response.flushBuffer();
		
	 return null;
	}

	
	@RequestMapping(method = RequestMethod.GET, value="/project/{projectId}/id/{uuid}/{type}")
	public ModelAndView id(@PathVariable Long projectId,@PathVariable String uuid, 
			@PathVariable String type,
			HttpServletResponse response, HttpServletRequest request) throws IOException{
		ExecutionContext ec= null;
		try{
			 ec=executionContextManager.getExecutionContextFromUuid(uuid);
		}catch(Exception ex){
			response.sendError(404);
			return null;
		}
		Workspace workspace=workspaceManager.getWorkspaceForProject(projectId, uuid);
		StringBuilder sbuilder = new StringBuilder();
		if(type.equalsIgnoreCase("commandline")){
			sbuilder.append(ec.getCommandLine());
		}else if(type.equalsIgnoreCase("error")){
			sbuilder.append(FileUtils.readFileToString(new File(workspace.getErrFile())));
		}else if(type.equalsIgnoreCase("output")){
			sbuilder.append(FileUtils.readFileToString(new File(workspace.getOutputFile())));
		}else if(type.equalsIgnoreCase("input")){
			sbuilder.append(FileUtils.readFileToString(new File(workspace.getInputFile())));
		}else if(type.equalsIgnoreCase("result")){
			sbuilder.append(FileUtils.readFileToString(new File(workspace.getResultFile())));
		}
		response.setContentType("text/plain");
		response.getWriter().print(sbuilder.toString());
		response.flushBuffer();
		
		
		return null;
		
	}


	private String getFileInfo(String fileName, String workspaceDir) {
		File file  = new File(fileName);
		int i = fileName.indexOf(workspaceDir);
		String name = file.getAbsolutePath().substring(i+workspaceDir.length());
		String length = FileUtils.byteCountToDisplaySize(file.length());
		return "<tr><td>"+name+"</td><td>"+length+"</td></tr>";
	}

}
