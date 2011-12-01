package org.imirsel.extractotron.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imirsel.extractotron.dao.LookupDao;
import org.imirsel.extractotron.model.Extractor;
import org.imirsel.extractotron.model.Role;
import org.imirsel.extractotron.model.SongCollection;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Hibernate implementation of LookupDao.
 *
 */
@Repository
public class LookupDaoHibernate implements LookupDao {
    private Log log = LogFactory.getLog(LookupDaoHibernate.class);
    private HibernateTemplate hibernateTemplate;

    /**
     * Initialize LookupDaoHibernate with Hibernate SessionFactory.
     * @param sessionFactory
     */
    @Autowired
    public LookupDaoHibernate(final SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        log.debug("Retrieving all role names...");

        return hibernateTemplate.find("from Role order by name");
    }

	public List<SongCollection> getSongCollections() {
		log.debug("Retrieving all SongCollection names...");
		return hibernateTemplate.find("from SongCollection order by name");
	}

	public List<Extractor> getExtractors() {
		return hibernateTemplate.find("from Extractor order by name");
	}
}
