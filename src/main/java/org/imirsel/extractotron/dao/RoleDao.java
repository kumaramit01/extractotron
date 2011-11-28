package org.imirsel.extractotron.dao;

import org.imirsel.extractotron.model.Role;

/**
 * Role Data Access Object (DAO) interface.
 *
 */
public interface RoleDao extends GenericDao<Role, Long> {
    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return populated role object
     */
    Role getRoleByName(String rolename);

    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    void removeRole(String rolename);
}
