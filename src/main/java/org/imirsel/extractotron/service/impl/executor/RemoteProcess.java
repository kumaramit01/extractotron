package org.imirsel.extractotron.service.impl.executor;

import org.imirsel.extractotron.model.ExecutionContext;

public class RemoteProcess {
	private long processId =-1;
	private int returnValue=-1;
	private String status;
	private ExecutionContext executionContext;
	public RemoteProcess(ExecutionContext ec) {
		this.executionContext = ec;
	}
	public long getProcessId() {
		return processId;
	}
	public void setProcessId(long processId) {
		this.processId = processId;
	}
	public int getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(int returnValue) {
		this.returnValue = returnValue;
	}
	public ExecutionContext getExecutionContext() {
		return executionContext;
	}
	public void setExecutionContext(ExecutionContext executionContext) {
		this.executionContext = executionContext;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
