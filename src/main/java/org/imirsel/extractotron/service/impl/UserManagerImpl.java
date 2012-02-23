package org.imirsel.extractotron.service.impl;

import org.imirsel.extractotron.dao.ProjectDao;
import org.imirsel.extractotron.dao.UserDao;
import org.imirsel.extractotron.dao.hibernate.ProjectNotFoundException;
import org.imirsel.extractotron.model.Project;
import org.imirsel.extractotron.model.User;
import org.imirsel.extractotron.service.UserExistsException;
import org.imirsel.extractotron.service.UserManager;
import org.imirsel.extractotron.service.UserService;
import org.imirsel.extractotron.webapp.controller.ProjectExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;
import java.util.Set;


/**
 * Implementation of UserManager interface.
 *
 */
@Service("userManager")
@WebService(serviceName = "UserService", endpointInterface = "org.imirsel.extractotron.service.UserService")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService {
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;
    private ProjectDao projectDao;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }
    
    @Autowired
    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    /**
     * {@inheritDoc}
     */
    public User getUser(String userId) {
        return userDao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }

        // Get and prepare password management-related artifacts
        boolean passwordChanged = false;
        if (passwordEncoder != null) {
            // Check whether we have to encrypt (or re-encrypt) the password
            if (user.getVersion() == null) {
                // New user, always encrypt
                passwordChanged = true;
            } else {
                // Existing user, check password in DB
                String currentPassword = userDao.getUserPassword(user.getUsername());
                if (currentPassword == null) {
                    passwordChanged = true;
                } else {
                    if (!currentPassword.equals(user.getPassword())) {
                        passwordChanged = true;
                    }
                }
            }

            // If password was changed (or new user), encrypt it
            if (passwordChanged) {
                user.setPassword(passwordEncoder.encodePassword(user.getPassword(), null));
            }
        } else {
            log.warn("PasswordEncoder not set, skipping password encryption...");
        }

        try {
            return userDao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        } catch (JpaSystemException e) { // needed for JPA
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeUser(String userId) {
        log.debug("removing user: " + userId);
        userDao.remove(new Long(userId));
    }

    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    public List<User> search(String searchTerm) {
        return super.search(searchTerm, User.class);
    }

	public Set<Project> getProjectsCurrentUser() {
		Authentication authn =SecurityContextHolder.getContext().getAuthentication();
		String username="";
		if(authn.getPrincipal() instanceof String){
			username = authn.getPrincipal().toString();
		}else if(authn.getPrincipal() instanceof User){
			User user = (User) authn.getPrincipal();
			username = user.getUsername();
		}else{
			throw new RuntimeException("Error getting principal : " + authn.getPrincipal());
		}
		User user1=getUserByUsername(username);
		return user1.getProjects();
	}

	public Project getProjectCurrentUser(Long id) throws ProjectNotFoundException {
		Set<Project> set=getProjectsCurrentUser();
		Project project = null;
		boolean found = false;
		for(Project p:set){
			System.out.println("ID =>"+ p.getId());
			if(p.getId().longValue() == id.longValue()){
			 project = p;
			 found = true;
			 break;
			}
		}
		if(found){
			return project;
		}else{
			throw new ProjectNotFoundException(" project with id " + id);
		}
	}

	public void saveProject(Project project) throws ProjectExistsException {
		try{
		   projectDao.saveProject(project);
		} catch (DataIntegrityViolationException e) {
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new ProjectExistsException("Project '" + project.getName() + "' already exists!");
        } catch (JpaSystemException e) { // needed for JPA
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new ProjectExistsException("Project '" + project.getName() + "' already exists!");
        }
        Authentication authn =SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authn.getPrincipal();
		User user1=getUserByUsername(user.getUsername());
		user1.getProjects().add(project);
		userDao.save(user1);
	
	}

	public void removeProject(String projectId) {
		Project project=projectDao.get(new Long(projectId));
		Authentication authn =SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authn.getPrincipal();
		User user1=getUserByUsername(user.getUsername());
		user1.getProjects().remove(project);
		userDao.save(user1);
		projectDao.remove(project.getId());
		
	}
}
