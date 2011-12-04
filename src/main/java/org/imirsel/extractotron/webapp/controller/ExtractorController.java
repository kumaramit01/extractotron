package org.imirsel.extractotron.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.service.ExtractorManager;
import org.imirsel.extractotron.service.LookupManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/extractor/*")
public class ExtractorController {
	private ExtractorManager extractorManager;
	@Autowired
	private LookupManager lookupManager;
	
	@Autowired
	public void setExtractorManager(ExtractorManager extractorManager) {
		this.extractorManager = extractorManager;
	}

	@RequestMapping(method = RequestMethod.GET, value="/search")
	public ModelAndView search(@RequestParam(required = false, value = "q") String query,
			HttpServletRequest request) throws Exception {

    	// set the available collections as the
        request.setAttribute("supportedFeatures", lookupManager.getSupportedFeatures());
        return new ModelAndView("extractor/extractorList", Constants.EXTRACTOR_LIST, extractorManager.search(query));
	}
	  
	@RequestMapping(method = RequestMethod.GET, value="/list")
	public ModelAndView list(HttpServletRequest request) throws Exception {
		// set the available collections as the
        request.setAttribute("supportedFeatures", lookupManager.getSupportedFeatures());
        return new ModelAndView("extractor/extractorList", Constants.EXTRACTOR_LIST, extractorManager.getAll());
	}

	@RequestMapping(method = RequestMethod.GET, value="/get")
	public ModelAndView get(@RequestParam(required = true, value = "id") String id,
			HttpServletRequest request) throws Exception {
        request.setAttribute("supportedFeatures", lookupManager.getSupportedFeatures());
        return new ModelAndView("extractor/extractorDetail", Constants.EXTRACTOR, extractorManager.get(new Long(id)));
	}

}
