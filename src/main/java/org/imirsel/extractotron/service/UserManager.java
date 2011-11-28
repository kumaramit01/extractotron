package org.imirsel.extractotron.service;

import org.imirsel.extractotron.dao.UserDao;
import org.imirsel.extractotron.dao.hibernate.ProjectNotFoundException;
import org.imirsel.extractotron.model.Project;
import org.imirsel.extractotron.model.User;
import org.imirsel.extractotron.webapp.controller.ProjectExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 */
public interface UserManager extends GenericManager<User, Long> {
    /**
     * Convenience method for testing - allows you to mock the DAO and set it on an interface.
     * @param userDao the UserDao implementation to use
     */
    void setUserDao(UserDao userDao);

    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId the identifier for the user
     * @return User
     */
    User getUser(String userId);

    /**
     * Finds a user by their username.
     * @param username the user's username used to login
     * @return User a populated user object
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException
     *         exception thrown when user not found
     */
    User getUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Retrieves a list of all users.
     * @return List
     */
    List<User> getUsers();

    /**
     * Saves a user's information.
     *
     * @param user the user's information
     * @throws UserExistsException thrown when user already exists
     * @return user the updated user object
     */
    User saveUser(User user) throws UserExistsException;

    /**
     * Removes a user from the database by their userId
     *
     * @param userId the user's id
     */
    void removeUser(String userId);

    /**
     * Search a user for search terms.
     * @param searchTerm the search terms.
     * @return a list of matches, or all if no searchTerm.
     */
    List<User> search(String searchTerm);

	Set<Project> getProjectsCurrentUser();

	Project getProjectCurrentUser(Long id) throws ProjectNotFoundException;

	void saveProject(Project project) throws ProjectExistsException;

	void removeProject(String string);
}
