package org.imirsel.extractotron.service;

import java.util.List;

import org.imirsel.extractotron.dao.CollectionDao;
import org.imirsel.extractotron.dao.hibernate.CollectionNotFoundException;
import org.imirsel.extractotron.model.LabelValue;
import org.imirsel.extractotron.model.SongCollection;



public interface CollectionManager extends GenericManager<SongCollection, Long>{
    void setCollectionDao(CollectionDao collectionDao);
    List<SongCollection> search(String searchTerm);
    public List<SongCollection> getCollections();
    public SongCollection getCollectionByName(String name) throws CollectionNotFoundException;
}
