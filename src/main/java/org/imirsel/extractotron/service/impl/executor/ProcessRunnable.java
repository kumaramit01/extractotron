package org.imirsel.extractotron.service.impl.executor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;



import org.compass.core.util.Assert;
import org.imirsel.extractotron.Constants;
import org.imirsel.extractotron.model.ExecutionContext;



import com.sun.jna.Pointer;
import com.sun.jna.examples.win32.Kernel32;
import com.sun.jna.examples.win32.W32API;



public class ProcessRunnable implements Runnable {
	private static final Logger LOG = Logger.getLogger(ProcessRunnable.class.getName());
	private final ConcurrentHashMap<String, Process> processHash;
	private final ConcurrentHashMap<String,RemoteProcess> remoteProcessHash;
	private final ConcurrentHashMap<String,RemoteProcessMonitor> remoteProcessMonitorHash;
	private BlockingQueue<ExecutionContext> taskQueue;
	// to synchronize access when two hash maps are modified at the same time
	private final Lock mapLock;
	private final String processId;
	
	
	
	
	public ProcessRunnable(final String id, 
			ConcurrentHashMap<String, Process> processHash, 
			ConcurrentHashMap<String,RemoteProcess> remoteProcessHash,
			ConcurrentHashMap<String,RemoteProcessMonitor> remoteProcessMonitorHash,
			BlockingQueue<ExecutionContext> taskQueue,
			Lock mapLock) {
		this.processId=id;
		this.taskQueue = taskQueue;
		this.processHash = processHash;
		this.remoteProcessHash = remoteProcessHash;
		this.remoteProcessMonitorHash = remoteProcessMonitorHash;
		this.mapLock = mapLock;
	}
	
	public String getProcessId(){
		return this.processId;
	}

	public void run() {
		ExecutionContext ec= taskQueue.remove();
		LOG.info("Execute... " + ec);
		RemoteProcess rp = this.remoteProcessHash.get(ec.getUuid());
		RemoteProcessMonitor remoteProcessMonitor =remoteProcessMonitorHash.get(ec.getUuid());
		Assert.isTrue(rp!=null);
		
		ProcessBuilder pb = new ProcessBuilder();
		pb.command(makeList(ec.getCommandLine()));
		pb.directory(new File(ec.getWorkingDirectory()));
		
		Process process = null;
		boolean success= true;
		String errorMessage=null;
		try {
			process = pb.start();
			if (process != null) {
				long pid = getProcessId(process);
				rp.setProcessId(pid);
				ec.setPid(pid);
				ec.setStatus(Constants.RUNNING);
				rp.setStatus(Constants.RUNNING);
				rp.getExecutionContext().setStatus(Constants.RUNNING);
				mapLock.lock();
				this.processHash.put(ec.getUuid(),process);
				mapLock.unlock();
				InputStream errorStream=process.getErrorStream();
				InputStream inputStream = process.getInputStream();
				drainStream("Error",errorStream,ec.getErrorLogFile());
				drainStream("output", inputStream,ec.getOutputFile());
				//rp.setErrorStream(new SimpleRemoteInputStream(new BufferedInputStream(process.getErrorStream())));
				//rp.setInputStream(new SimpleRemoteInputStream(new BufferedInputStream(process.getInputStream())));
				remoteProcessMonitor.processStarted(rp);
				// wait for the process
				int val =-1;
				try {
					val = process.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally{
					rp.setReturnValue(val);
				}
				if (val == 0) {
					LOG.info("Finished the process: ");
					rp.getExecutionContext().setStatus(Constants.FINISHED);
					rp.setStatus(Constants.FINISHED);
					remoteProcessMonitor.processFinished(rp);
				} else {
					LOG.info("Process error: ");
					rp.getExecutionContext().setStatus(Constants.FINISHED_WITH_ERROR);
					rp.setStatus(Constants.FINISHED_WITH_ERROR);
					remoteProcessMonitor.processError(rp);
				}
				
			} else { // process is null
				LOG.info("process is null...");
				success=false;
				errorMessage ="process.start returned null -this should not happen";
			}
		} catch (NullPointerException npe) {
			LOG.info("NPE Process failed: " + npe.getMessage());
			npe.printStackTrace();
			success=false;
			errorMessage = npe.getMessage();
		} catch (IndexOutOfBoundsException iobe) {
			LOG.info("Index Out of Bound Process failed: " + iobe.getMessage());
			iobe.printStackTrace();
			success=false;
			errorMessage = iobe.getMessage();
		} catch (SecurityException se) {
			LOG.info("Security Exception Process failed: " + se.getMessage());
			se.printStackTrace();
			success=false;
			errorMessage = se.getMessage();
		} catch (IOException ioe) {
			LOG.info("IO Exception Process failed: " + ioe.getMessage());
			ioe.printStackTrace();
			success=false;
			errorMessage = ioe.getMessage();
		} finally {
			if(!success){
				ec.setStatus(Constants.PROCESS_EXECUTION_FAILED);
				rp.setStatus(Constants.PROCESS_EXECUTION_FAILED);
				remoteProcessMonitor.processFailed(rp, errorMessage);
			}
			mapLock.lock();
			this.processHash.remove(ec.getUuid());
			this.remoteProcessHash.remove(ec.getUuid());
			mapLock.unlock();
		}
		LOG.info("returning from the ProcessRunner");
	}

	

	
	private String[] makeList(String commandLine) {
		return commandLine.split("\\s+");
	}

	private void drainStream(String name, InputStream stream, String file) throws FileNotFoundException {
	     FileOutputStream fos = new FileOutputStream(file);
		DrainStream ds = new DrainStream("Stream drainer: "+name, new BufferedInputStream(stream),
				new BufferedOutputStream(fos));
		new Thread(ds).start();
	}

	private long getProcessId(Process process) {
		long pid = -1l;
		if (process.getClass().getName().equals("java.lang.UNIXProcess")) {
			try {
				Field f = process.getClass().getDeclaredField("pid");
				f.setAccessible(true);
				pid = f.getInt(process);
			} catch (Throwable e) {
				// swallow -don't care
			}
		} else if (process.getClass().getName()
				.equals("java.lang.Win32Process")
				|| process.getClass().getName().equals("java.lang.ProcessImpl")) {
			try {
				Field f = process.getClass().getDeclaredField("handle");
				f.setAccessible(true);
				long handl = f.getLong(process);
				Kernel32 kernel = Kernel32.INSTANCE;
				W32API.HANDLE handle = new W32API.HANDLE();
				handle.setPointer(Pointer.createConstant(handl));
				pid = kernel.GetProcessId(handle);
			} catch (Throwable e) {
				// swallow -don't care
			}
		}
		return pid;
	}

	
}
