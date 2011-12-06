package org.imirsel.extractotron.service.impl;


import org.imirsel.extractotron.dao.ExecutionContextDao;

import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.service.ExecutionContextManager;
import org.imirsel.extractotron.service.ExecutionContextService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;


/**
 * Implementation of ExecutionContextManager interface.
 *
 */
@Service("executionContextManager")
@WebService(serviceName = "ExecutionContextService", endpointInterface = "org.imirsel.extractotron.service.ExecutionContextService")
public class ExecutionContextManagerImpl extends GenericManagerImpl<ExecutionContext, Long> implements ExecutionContextManager, ExecutionContextService {

	private ExecutionContextDao executionContextDao;

	
	public List<ExecutionContext> getExecutionContexts() {
		return this.dao.getAllDistinct();
	}
	
	public ExecutionContext getExecutionContext(String id){
		return this.dao.get(new Long(id));
	}


	

	@Autowired
    public void setExecutionContextDao(ExecutionContextDao executionContextDao) {
		this.dao = executionContextDao;
        this.executionContextDao = executionContextDao;
    }



    
    

    /**
     * {@inheritDoc}
     */
    public List<ExecutionContext> search(String searchTerm) {
        return super.search(searchTerm, ExecutionContext.class);
    }


	

	

	
}
