package org.imirsel.extractotron.service.impl;


import org.imirsel.extractotron.dao.ExtractorDao;
import org.imirsel.extractotron.dao.LookupDao;
import org.imirsel.extractotron.model.LabelValue;
import org.imirsel.extractotron.model.Role;
import org.imirsel.extractotron.model.Extractor;
import org.imirsel.extractotron.service.ExtractorManager;
import org.imirsel.extractotron.service.ExtractorNotFoundException;
import org.imirsel.extractotron.service.ExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;


/**
 * Implementation of ExtractorManager interface.
 *
 */
@Service("extractorManager")
@WebService(serviceName = "ExtractorService", endpointInterface = "org.imirsel.extractotron.service.ExtractorService")
public class ExtractorManagerImpl extends GenericManagerImpl<Extractor, Long> implements ExtractorManager, ExtractorService {

	private ExtractorDao extractorDao;


	@Autowired
    public void setExtractorDao(ExtractorDao extractorDao) {
		this.dao = extractorDao;
        this.extractorDao = extractorDao;
    }


    /**
     * {@inheritDoc}
     */
    public Extractor getExtractor(String name) {
        return extractorDao.getExtractorByName(name);
    }

    /**
     * {@inheritDoc}
     */
    public List<Extractor> getExtractors() {
        return extractorDao.getAllDistinct();
    }

    
    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public Extractor getExtractorByName(String name) throws ExtractorNotFoundException {
        return (Extractor) extractorDao.loadExtractorByName(name);
    }
    
    public Extractor getExtractorByCommandLine(String commandLine) throws ExtractorNotFoundException {
        return (Extractor) extractorDao.loadExtractorByCommandLine(commandLine);
    }


    /**
     * {@inheritDoc}
     */
    public List<Extractor> search(String searchTerm) {
        return super.search(searchTerm, Extractor.class);
    }


	

	
}
