package org.imirsel.extractotron.dao;

import org.imirsel.extractotron.model.Extractor;
import org.imirsel.extractotron.model.LabelValue;
import org.imirsel.extractotron.model.Role;
import org.imirsel.extractotron.model.SongCollection;

import java.util.List;

/**
 * Lookup Data Access Object (GenericDao) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 */
public interface LookupDao {
    //~ Methods ================================================================

    /**
     * Returns all Roles ordered by name
     * @return populated list of roles
     */
    List<Role> getRoles();
    List<SongCollection> getSongCollections();
	List<Extractor> getExtractors();
	List<LabelValue> getRunPhases();
}
