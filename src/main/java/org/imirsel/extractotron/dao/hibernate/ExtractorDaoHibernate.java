package org.imirsel.extractotron.dao.hibernate;

import java.util.List;

import org.imirsel.extractotron.dao.ExtractorDao;
import org.imirsel.extractotron.model.Extractor;
import org.imirsel.extractotron.model.SongCollection;
import org.imirsel.extractotron.service.ExtractorNotFoundException;
import org.springframework.stereotype.Repository;


@Repository("extractorDao")
public class ExtractorDaoHibernate extends GenericDaoHibernate<Extractor, Long> implements ExtractorDao{

    /**
     * Constructor that sets the entity to SongCollection.class
     */
    public ExtractorDaoHibernate() {
        super(Extractor.class);
    }
	
	public List<Extractor> getExtractors() {
        return getHibernateTemplate().find("from Extractor u order by upper(u.name)");
	}
	
	  /**
     * {@inheritDoc}
     */
    public Extractor getExtractorByName(String name) {
        List collections = getHibernateTemplate().find("from Extractor where name=?", name);
        if (collections.isEmpty()) {
            return null;
        } else {
            return (Extractor) collections.get(0);
        }
    }
    
    public Extractor loadExtractorByName(String name) throws ExtractorNotFoundException {
        List collections = getHibernateTemplate().find("from Extractor where name=?", name);
        if (collections == null || collections.isEmpty()) {
            throw new ExtractorNotFoundException("name '" + name + "' not found...");
        } else {
            return  (Extractor) collections.get(0);
        }
    }
    
    public Extractor loadExtractorByCommandLine(String commandLine) throws ExtractorNotFoundException {
        List collections = getHibernateTemplate().find("from Extractor where commandLine=?", commandLine);
        if (collections == null || collections.isEmpty()) {
            throw new ExtractorNotFoundException("commandLine '" + commandLine + "' not found...");
        } else {
            return  (Extractor) collections.get(0);
        }
    }

	

}
