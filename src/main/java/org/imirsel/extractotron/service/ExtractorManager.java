package org.imirsel.extractotron.service;

import java.util.List;

import org.imirsel.extractotron.dao.ExtractorDao;
import org.imirsel.extractotron.model.Extractor;



public interface ExtractorManager extends GenericManager<Extractor, Long>{
    void setExtractorDao(ExtractorDao extractorDao);
    List<Extractor> search(String searchTerm);
    public List<Extractor> getExtractors();
    public Extractor getExtractorByName(String name) throws ExtractorNotFoundException;
}
