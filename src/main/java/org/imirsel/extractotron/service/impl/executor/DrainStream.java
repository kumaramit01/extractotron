package org.imirsel.extractotron.service.impl.executor;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;




/**
 * Reads the stream in a thread and writes to an output stream
 * 
 * @author kumaramit01
 * @since 0.2.0
 */
class DrainStream implements Runnable {
	private String name;
	private BufferedInputStream inputStream;
	private BufferedOutputStream os;
	
	public DrainStream(String name, BufferedInputStream inputStream,BufferedOutputStream os) {
		this.name = name;
		this.inputStream = inputStream;
		this.os = os;
	}

	public void run() {
		System.err.println("now reading the out stream... "
				+ Thread.currentThread().getId() + " "+ this.name );
		try {
			 int byte_;
		     while ((byte_ = inputStream.read ()) != -1){
		    	 os.write (byte_);
		     }
		     os.flush();
		} catch (IOException e) {
			System.err.println("Error: thrown exception " + e.getMessage());
			e.printStackTrace();
		}finally{
			// nothing to do here.
		}

	}

}