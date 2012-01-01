package org.imirsel.extractotron.service.impl.executor;

/**
 * 
 */


import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Nema Process that could not be executed and rejected are caught in this class.
 * 
 * @author kumaramit01
 *
 */
public class NemaRejectedExecutionHandler implements RejectedExecutionHandler {

	/* (non-Javadoc)
	 * @see java.util.concurrent.RejectedExecutionHandler#rejectedExecution(java.lang.Runnable, java.util.concurrent.ThreadPoolExecutor)
	 */
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		System.err.println("Task Rejected: " + runnable.toString());
		throw new RejectedExecutionException();
	}

}
