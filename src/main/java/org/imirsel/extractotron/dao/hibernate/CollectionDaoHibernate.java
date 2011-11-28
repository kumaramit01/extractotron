package org.imirsel.extractotron.dao.hibernate;

import java.util.List;

import org.imirsel.extractotron.dao.CollectionDao;
import org.imirsel.extractotron.model.SongCollection;
import org.springframework.stereotype.Repository;


@Repository("collectionDao")
public class CollectionDaoHibernate extends GenericDaoHibernate<SongCollection, Long> implements CollectionDao{

    /**
     * Constructor that sets the entity to SongCollection.class
     */
    public CollectionDaoHibernate() {
        super(SongCollection.class);
    }
	
	public List<SongCollection> getCollections() {
        return getHibernateTemplate().find("from SongCollection u order by upper(u.name)");
	}
	
    /** 
     * {@inheritDoc}
    */
    public SongCollection loadCollectionByName(String name) throws CollectionNotFoundException {
        List collections = getHibernateTemplate().find("from SongCollection where name=?", name);
        if (collections == null || collections.isEmpty()) {
            throw new CollectionNotFoundException("name '" + name + "' not found...");
        } else {
            return  (SongCollection) collections.get(0);
        }
    }

}
