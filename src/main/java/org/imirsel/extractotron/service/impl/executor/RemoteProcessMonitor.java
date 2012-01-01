package org.imirsel.extractotron.service.impl.executor;


/**
 *  Allows users to monitor a process life cycle
 * 
 *  Different State Transitions <br/>
 *  SUBMITTED->REJECTED <br/>
 *	SUBMITTED->FAILED <br/>
 *	SUBMITTED->STARTED->INVALID RESULT->FINISHED <br/>
 *	SUBMITTED->STARTED->RESULT->FINISHED <br/>
 *	SUBMITTED->STARTED->PROCESS ERROR (returns non zero value)->FINISHED <br/>
 *   
 *  SUBMITTED->QUEUED <br/>
 *  SUBMITTED->QUEUED->DEQUEUED->STARTED <br/>
 *  SUBMITTED->QUEUED->DEQUEUED->FAILED <br/>
 *   
 *  // happens when the executor pool is shutting down <br/>
 *  SUBMITTED->QUEUED->DEQUEUED->REJECTED <br/>
 *  <p>
 *  processSubmitted and processRejected gets called by a different thread. All other methods
 *  are called by the same thread.
 * </p>
 * @author kumaramit01
 * @since 0.1.0
 * @version 0.6.0 -Added processRejected, processQueued and processDequeued methods
 */
public interface RemoteProcessMonitor{
	
	/** Called when the process is dequeued
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 */
	public void processDequeued(RemoteProcess process);
	
	/**Called when the process is queued
	 * @param process the remote process {@code RemoteProcess}
	 * @param count
	 * @param queueSize
	 */
	public void processQueued(RemoteProcess process, final int count, final int queueSize);
	
	/** Called when the process is rejected and cannot be run.
	 *  
	 * @param process the remote process {@code RemoteProcess}
	 * @throws RemoteException
	 */
	public void processRejected(RemoteProcess process);
	
	/**Calls when the process starts -provides valid pid
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 * @throws RemoteException
	 */
	public void processStarted(RemoteProcess process);
	/**Called when the process is aborted -has valid pid and return value
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 * @throws RemoteException
	 */
	public void processAborted(RemoteProcess process);
	/**Called when the process finishes
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 * @throws RemoteException
	 */
	public void processFinished(RemoteProcess process);
	/**Called when the process is submitted -has invalid process id
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 * @throws RemoteException
	 */
	public void processSubmitted(RemoteProcess process);
	/**Called when the process could not be executed
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 * @param message
	 * @throws RemoteException
	 */
	public void processFailed(RemoteProcess process,final String message);
	/**Called when the process ends because of the error.
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 * @throws RemoteException
	 */
	public void processError(RemoteProcess process);
	
	
	/**Called when the process fails to return within a particular set time.
	 * 
	 * @param process the remote process {@code RemoteProcess}
	 * @throws RemoteException
	 */
	public void processTimeout(RemoteProcess process);
	
	
}
