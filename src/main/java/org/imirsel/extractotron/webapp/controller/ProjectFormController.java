package org.imirsel.extractotron.webapp.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.model.Project;
import org.imirsel.extractotron.model.Role;
import org.imirsel.extractotron.model.User;
import org.imirsel.extractotron.service.CollectionManager;
import org.imirsel.extractotron.service.LookupManager;
import org.imirsel.extractotron.service.ProjectManager;
import org.imirsel.extractotron.service.UserExistsException;
import org.imirsel.extractotron.service.UserManager;
import org.imirsel.extractotron.webapp.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/projectform*")
public class ProjectFormController extends BaseFormController{
	
	private UserManager userManager;
	private ProjectManager projectManager;
	private CollectionManager collectionManager;
	private LookupManager lookupManager;
	
	@Autowired
	public void setLookupManager(LookupManager lookupManager) {
		this.lookupManager = lookupManager;
	}
	
	@Autowired
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
	@Autowired
	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}
	@Autowired
	public void setCollectionManager(CollectionManager collectionManager) {
		this.collectionManager = collectionManager;
	}
	
    public ProjectFormController() {
	        setCancelView("redirect:/mainMenu");
	        setSuccessView("redirect:/project/list");
	}
	
    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(Project project, BindingResult errors, HttpServletRequest request,
                           HttpServletResponse response)
            throws Exception {
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }

        if (validator != null) { // validator is null during testing
            validator.validate(project, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "projectform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            userManager.removeProject(project.getId().toString());
            saveMessage(request, getText("project.deleted", project.getName(), locale));
            return getSuccessView();
        } else {

            // only attempt to change roles if user is admin for other users,
            // showForm() method will handle populating
            if (request.isUserInRole(Constants.ADMIN_ROLE)) {
              // if Admin is here
            }

          
            try {
                userManager.saveProject(project);
            } catch (AccessDeniedException ade) {
                // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                log.warn(ade.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            } catch (ProjectExistsException e) {
                errors.rejectValue("projectname", "errors.existing.project",
                        new Object[]{project.getName(), project.getStatus()}, "duplicate project");

                return "projectform";
            }

            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                saveMessage(request, getText("project.saved", project.getName(), locale));

                // return to main Menu
                return getCancelView();
            } else {
                    saveMessage(request, getText("project.updated.byAdmin", project.getName(), locale));
               
            }
        }

        return "projectform";
    }

    @ModelAttribute
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    protected Project showForm(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	// set the available collections as the
        request.setAttribute("availableCollections", lookupManager.getAllCollections());
    	
    	
    	
    	
        if (!isFormSubmission(request)) {
            String projectId = request.getParameter("projectId");

            Project project = null;
            if (projectId != null && !isAdd(request)) {
               project = userManager.getProjectCurrentUser(new Long(projectId));
            }else{
            	project = new Project();
            }

            return project;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
        	Long pid = new Long(request.getParameter("id"));
            return getUserManager().getProjectCurrentUser(pid);
        }
    }

    private boolean isFormSubmission(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(HttpServletRequest request) {
        String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
    
    
    

}
