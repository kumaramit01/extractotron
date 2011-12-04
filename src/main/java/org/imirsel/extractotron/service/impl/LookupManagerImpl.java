package org.imirsel.extractotron.service.impl;

import org.imirsel.extractotron.dao.LookupDao;
import org.imirsel.extractotron.model.Extractor;
import org.imirsel.extractotron.model.LabelValue;
import org.imirsel.extractotron.model.Role;
import org.imirsel.extractotron.model.SongCollection;
import org.imirsel.extractotron.service.LookupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 */
@Service("lookupManager")
public class LookupManagerImpl implements LookupManager {
    @Autowired
    LookupDao dao;

    /**
     * {@inheritDoc}
     */
    public List<LabelValue> getAllRoles() {
        List<Role> roles = dao.getRoles();
        List<LabelValue> list = new ArrayList<LabelValue>();

        for (Role role1 : roles) {
            list.add(new LabelValue(role1.getName(), role1.getName()));
        }

        return list;
    }

	public List<LabelValue> getAllCollections() {
		List<SongCollection> collections = dao.getSongCollections();
		List<LabelValue> list = new ArrayList<LabelValue>();
		for(SongCollection sc:collections){
			list.add(new LabelValue(sc.getName(),sc.getName()));
		}
		return list;
	}

	public List<LabelValue> getSupportedFeatures() {
		LabelValue[] list = new LabelValue[]{
				new LabelValue("Beat","beat"),
				new LabelValue("Linear Prediction Cepstral Coefficients","lpcc"),
				new LabelValue("Line Spectral Pair","lsp"),
				new LabelValue("Mel-Frequency Cepstral Coefficients","mfcc"),
				new LabelValue("Spectral Crest Factor","scf"),
				new LabelValue("Spectral Flatness Measure","sfm"),
				new LabelValue("SCF and SFM features","sfmscf"),
				new LabelValue("Centroid, Rolloff, Flux, ZeroCrossings","stft"),
				new LabelValue("Centroid, Rolloff Flux, ZeroCrossings, Mel-frequency Cepstral Coefficients","stftmfcc")};
		return Arrays.asList(list);
	}

	public List<LabelValue> getAllExtractors() {
		List<Extractor> collections = dao.getExtractors();
		List<LabelValue> list = new ArrayList<LabelValue>();
		for(Extractor sc:collections){
			list.add(new LabelValue(sc.getName(),sc.getCommandLine()));
		}
		return list;
	}
	
	
	public List<LabelValue> getRunPhases(){
		return dao.getRunPhases();
	}
	
}
