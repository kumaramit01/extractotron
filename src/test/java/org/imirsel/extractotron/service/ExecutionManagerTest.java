package org.imirsel.extractotron.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.compass.core.util.Assert;
import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.service.impl.executor.RemoteProcess;
import org.imirsel.extractotron.service.impl.executor.RemoteProcessMonitor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExecutionManagerTest extends BaseManagerTestCase{
	@Autowired
	private ExecutionManager executionManager;

	@Test
	public void testExecute() throws InterruptedException {
		Assert.isTrue(executionManager!=null);
		RemoteProcessMonitor rpm = new TestProcessMonitor();
		
		RemoteProcess rp= executionManager.execute(getExecutionContext(), rpm);
		RemoteProcess rp1= executionManager.execute(getExecutionContext(),rpm);
		
		try{
			Thread.sleep(10000);
		}catch(Exception ex){
			
		}
		Assert.isTrue(rp.getProcessId()!=-1);
		Assert.isTrue(rp1.getProcessId()!=-1);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("RP: "+rp.getReturnValue());
		System.out.println("RP1: "+ rp1.getReturnValue());
		
		Assert.isTrue(rp.getReturnValue()==0);
		Assert.isTrue(rp1.getReturnValue()==0);
	}
	
	public ExecutionContext getExecutionContext(){
		String id=UUID.randomUUID().toString();
		File file = new File("/tmp/"+id);
		file.mkdir();
		ExecutionContext ec = new ExecutionContext();
		String commandLine="/share/apps/marsyas/bin/bextract -fe beat -sfm -lpcc -lsp /tmp/input -w /tmp/output";
		ec.setCommandLine(commandLine);
		ec.setErrorLogFile("/tmp/"+id+"/err.txt");
		ec.setInputFile("/tmp/"+id+"/input.txt");
		ec.setName("test");
		ec.setOutputFile("/tmp/"+id+"/out.txt");
		ec.setResultFile("/tmp/"+id+"/result");
		ec.setTimeCreated(new Date());
		ec.setWorkingDirectory("/tmp/"+id+"/");
		ec.setUuid(id);
		return ec;
	}

	@Test
	public void testAbort() {
		fail("Not yet implemented");
	}

	class TestProcessMonitor implements RemoteProcessMonitor{

		@Override
		public void processDequeued(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processQueued(RemoteProcess process, int count,
				int queueSize) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processRejected(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processStarted(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processAborted(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processFinished(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processSubmitted(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processFailed(RemoteProcess process, String message) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processError(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void processTimeout(RemoteProcess process) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
