package org.imirsel.extractotron.webapp.controller;

import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.service.CollectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/collection")
public class CollectionController {
	
	private CollectionManager collectionManager = null;

	@Autowired
	public void setCollectionManager(CollectionManager collectionManager) {
		this.collectionManager = collectionManager;
	}
	
	 @RequestMapping(method = RequestMethod.GET,value="/get")
	 public ModelAndView get(@RequestParam(required = true, value = "id") Long id) throws Exception {
	   return new ModelAndView("collection/collectionDetail", Constants.COLLECTION, collectionManager.get(id));
	 }
	

	 @RequestMapping(method = RequestMethod.GET,value="/search")
	 public ModelAndView search(@RequestParam(required = false, value = "q") String query) throws Exception {
	   return new ModelAndView("collection/collectionList", Constants.COLLECTION_LIST, collectionManager.search(query));
	 }
	 
	 @RequestMapping(method = RequestMethod.GET,value="/list")
	 public ModelAndView list(@RequestParam(required = false, value = "q") String query) throws Exception {
	   return new ModelAndView("collection/collectionList", Constants.COLLECTION_LIST, collectionManager.getAll());
	 }
	
	
	

}
