package org.imirsel.extractotron.service;

import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.service.impl.executor.RemoteProcess;

public interface ExecutionManager {
	
	public RemoteProcess execute(final ExecutionContext ec) throws InterruptedException;
	public void abort(long pid);
	

}
