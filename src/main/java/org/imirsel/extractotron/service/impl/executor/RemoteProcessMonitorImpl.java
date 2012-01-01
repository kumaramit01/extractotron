package org.imirsel.extractotron.service.impl.executor;

import java.util.Date;

import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.service.ExecutionContextManager;

public class RemoteProcessMonitorImpl implements RemoteProcessMonitor {
	
	private ExecutionContextManager executionContextManager;
	
	public RemoteProcessMonitorImpl(ExecutionContextManager executionContextManager){
		this.executionContextManager = executionContextManager;
	}
	

	@Override
	public void processDequeued(RemoteProcess process) {
		System.out.println("processDequeued");
	
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);

	}

	@Override
	public void processQueued(RemoteProcess process, int count, int queueSize) {
		System.out.println("processQueued");
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);

		
	}

	@Override
	public void processRejected(RemoteProcess process) {
		System.out.println("processRejected");
		process.getExecutionContext().setTimeEnded(new Date());
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);


	}

	@Override
	public void processStarted(RemoteProcess process) {
		System.out.println("processStarted");
		process.getExecutionContext().setTimeStarted(new Date());
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);


	}

	@Override
	public void processAborted(RemoteProcess process) {
		System.out.println("processAborted");
		process.getExecutionContext().setTimeEnded(new Date());
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);


	}

	@Override
	public void processFinished(RemoteProcess process) {
		System.out.println("processFinished");
		process.getExecutionContext().setTimeEnded(new Date());
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);

	}

	@Override
	public void processSubmitted(RemoteProcess process) {
		System.out.println("processSubmitted");
		
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);

	}

	@Override
	public void processFailed(RemoteProcess process, String message) {
		System.out.println("processFailed");
		process.getExecutionContext().setTimeEnded(new Date());
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);


	}

	@Override
	public void processError(RemoteProcess process) {
		System.out.println("processError");
		process.getExecutionContext().setTimeEnded(new Date());
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);


	}

	@Override
	public void processTimeout(RemoteProcess process) {
		System.out.println("processTimeout");
		this.executionContextManager.save(process.getExecutionContext());
		ExecutionContext ec= executionContextManager.getExecutionContextFromUuid(process.getExecutionContext().getUuid());
		System.out.println(ec);


	}

}
