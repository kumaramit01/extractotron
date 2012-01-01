package org.imirsel.extractotron.dao;

import org.imirsel.extractotron.model.ExecutionContext;

public interface ExecutionContextDao extends GenericDao<ExecutionContext, Long>{
	public ExecutionContext getExecutionContextFromUuid(String uuid);

}
