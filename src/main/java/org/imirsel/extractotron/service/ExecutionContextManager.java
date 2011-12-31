package org.imirsel.extractotron.service;

import java.util.List;

import org.imirsel.extractotron.dao.ExecutionContextDao;
import org.imirsel.extractotron.model.ExecutionContext;



public interface ExecutionContextManager extends GenericManager<ExecutionContext, Long>{
    void setExecutionContextDao( ExecutionContextDao executionContextDao);
    List<ExecutionContext> search(String searchTerm);
    public List<ExecutionContext> getExecutionContexts();
    public ExecutionContext save(ExecutionContext ec);
}
