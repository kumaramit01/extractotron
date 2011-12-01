package org.imirsel.extractotron.dao;

import java.util.List;

import org.imirsel.extractotron.dao.hibernate.CollectionNotFoundException;
import org.imirsel.extractotron.model.SongCollection;



public interface CollectionDao extends GenericDao<SongCollection, Long>  {

	 /**
     * Gets a list of collections ordered by the uppercase version of their name.
     *
     * @return List populated list of song collection
     */
    List<SongCollection> getCollections();

	SongCollection loadCollectionByName(String name) throws CollectionNotFoundException;

	SongCollection getSongCollectionByName(String name);

}
