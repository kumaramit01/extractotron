package org.imirsel.extractotron.service.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.model.ExecutionContext;
import org.imirsel.extractotron.service.ExecutionManager;
import org.imirsel.extractotron.service.impl.executor.*;
import org.springframework.stereotype.Service;

@Service("executionManager")
public class ExecutionManagerImpl implements ExecutionManager {
	
	int corePoolSize=5;
	int maximumPoolSize=10;
	int queueSize=20;
	private ConcurrentHashMap<String, Process> processHash = new ConcurrentHashMap<String, Process>();
	private final ConcurrentHashMap<String,RemoteProcess> remoteProcessHash = new ConcurrentHashMap<String,RemoteProcess>();

	private final BlockingQueue<ExecutionContext> taskQueue = new LinkedBlockingQueue<ExecutionContext>();
 	
	private ReentrantLock lock = new ReentrantLock();
	ThreadPoolExecutor executor = new CustomThreadPoolExecutor(corePoolSize, maximumPoolSize, 0L, TimeUnit.MILLISECONDS, 
			new LinkedBlockingQueue<Runnable>(queueSize), new NemaRejectedExecutionHandler());

	@Override
	public RemoteProcess execute(final ExecutionContext ec) throws InterruptedException {
		this.taskQueue.add(ec);
		ProcessRunnable processRunner = new ProcessRunnable(ec.getUuid(), processHash, remoteProcessHash,taskQueue, lock);
		
		lock.lock();
		RemoteProcess remoteProcess = new RemoteProcess(ec);
		try{
			remoteProcessHash.put(ec.getUuid(),remoteProcess);
		}finally{
		lock.unlock();
		}
		try{
			NemaFutureTask<ProcessRunnable, Object> nemaTask = new NemaFutureTask<ProcessRunnable,Object>(processRunner,null);
			if(this.getMaxConcurrentProcess()>= executor.getActiveCount()){
				int count = executor.getActiveCount();
				int queueSize= executor.getQueue().size();
				ec.setStatus(Constants.QUEUED);
				remoteProcess.setStatus(Constants.QUEUED);
				//remoteProcess.getProcessExecutionInfo().setStatusCode(ProcessExecutionStatus.QUEUED.getCode());
				//remoteProcessMonitor.processQueued(remoteProcess, count, queueSize);
			}
			executor.execute(nemaTask);
		}catch(RejectedExecutionException rejectExecutionException){
			try{
				lock.lock();
		    //remoteProcessHash.remove(processExecutionProperties.getId());
				taskQueue.remove(ec);
		    //remoteProcessMonitorHash.remove(remoteProcess.getId());
			}finally{
				ec.setStatus(Constants.REJECTED);
				remoteProcess.setStatus(Constants.REJECTED);
				lock.unlock();
		    //remoteProcess.getProcessExecutionInfo().setStatusCode(ProcessExecutionStatus.REJECT.getCode());
		    //remoteProcessMonitor.processRejected(remoteProcess);
			}
		}
		return remoteProcess;
	}

	/**
	 * Return the maximum number of jobs that can be concurrently run 
	 * 
	 * @return the maximum number of jobs that can be concurrently run
	 * @since 0.6.0
	 */
	public int getMaxConcurrentProcess(){
		return queueSize;
	}
	

	public long getCompletedProcessCount(){
		synchronized(executor){
		return executor.getCompletedTaskCount();
		}
	}

	@Override
	public void abort(long pid) {
		// TODO Auto-generated method stub

	}

}
