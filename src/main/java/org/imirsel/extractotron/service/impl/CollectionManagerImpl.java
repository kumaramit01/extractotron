package org.imirsel.extractotron.service.impl;


import org.imirsel.extractotron.dao.CollectionDao;
import org.imirsel.extractotron.dao.LookupDao;
import org.imirsel.extractotron.dao.hibernate.CollectionNotFoundException;
import org.imirsel.extractotron.model.LabelValue;
import org.imirsel.extractotron.model.SongCollection;
import org.imirsel.extractotron.service.CollectionManager;
import org.imirsel.extractotron.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;


/**
 * Implementation of CollectionManager interface.
 *
 */
@Service("collectionManager")
@WebService(serviceName = "CollectionService", endpointInterface = "org.imirsel.extractotron.service.CollectionService")
public class CollectionManagerImpl extends GenericManagerImpl<SongCollection, Long> implements CollectionManager, CollectionService {

	private CollectionDao collectionDao;


	@Autowired
    public void setCollectionDao(CollectionDao collectionDao) {
		this.dao = collectionDao;
        this.collectionDao = collectionDao;
    }

    /**
     * {@inheritDoc}
     */
    public SongCollection getCollection(String id) {
        return collectionDao.get(new Long(id));
    }

    /**
     * {@inheritDoc}
     */
    public List<SongCollection> getCollections() {
        return collectionDao.getAllDistinct();
    }

    
    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public SongCollection getCollectionByName(String name) throws CollectionNotFoundException {
        return (SongCollection) collectionDao.loadCollectionByName(name);
    }

    /**
     * {@inheritDoc}
     */
    public List<SongCollection> search(String searchTerm) {
        return super.search(searchTerm, SongCollection.class);
    }


	

	
}
