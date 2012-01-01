package org.imirsel.extractotron.service.impl.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/** Nema Future Task implementation that has setter/getter 
 *  the process id.
 * @author kumaramit01
 * @since 0.6.0
 */
public class NemaFutureTask<V,T> extends FutureTask {
	private String id;

	/**public constructor
	 * 
	 * @param callable
	 */
	public NemaFutureTask(Callable<V> callable) {
		super(callable);
	}

	/**public constructor
	 * 
	 * @param runnable
	 * @param result
	 */
	public NemaFutureTask(Runnable runnable, T result) {
		super(runnable, result);
		ProcessRunnable prunner = (ProcessRunnable) runnable;
		setId(prunner.getProcessId());
	}
	protected void done() {
        System.out.println("Done!");
        try {
            get();
        } catch (InterruptedException e) {
           System.out.println("Task interrupted");
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
        }
    }

	/** Set the process id
	 * @param id
	 */
	private void setId(String id) {
		this.id = id;
	}

	/** Return the process id
	 * @return process id
	 */
	public String getId() {
		return id;
	}
	
	
	/**Cancel a future task
	 * 
	 * @return true/false boolean
	 * 
	 */
	public boolean cancel(boolean mayInterruptIfRunning){
		return super.cancel(mayInterruptIfRunning);
	}

}
