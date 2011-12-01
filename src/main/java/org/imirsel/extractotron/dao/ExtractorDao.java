package org.imirsel.extractotron.dao;

import org.imirsel.extractotron.model.Extractor;
import org.imirsel.extractotron.service.ExtractorNotFoundException;


public interface ExtractorDao extends GenericDao<Extractor, Long> {
	 public Extractor getExtractorByName(String name) ;

	public Extractor loadExtractorByName(String name) throws ExtractorNotFoundException;
}
