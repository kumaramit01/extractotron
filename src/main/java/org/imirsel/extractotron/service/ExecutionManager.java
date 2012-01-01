package org.imirsel.extractotron.service;

import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.service.impl.executor.RemoteProcess;
import org.imirsel.extractotron.service.impl.executor.RemoteProcessMonitor;

public interface ExecutionManager {
	
	public RemoteProcess execute(final ExecutionContext ec, final RemoteProcessMonitor rpm) throws InterruptedException;
	public void abort(long pid);
	public RemoteProcess getRemoteProcess(String remoteProcessUuid);
	

}
