package org.imirsel.extractotron.model;

/** Abstraction of the file system location where the
 * results of the execution are stored.
 * 
 * @author amitku
 *
 */
public class Workspace {
	
	private String directory;
	private String inputFile;
	private String outputFile;
	private String logDirectory;
	
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getInputFile() {
		return inputFile;
	}
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
	public String getLogDirectory() {
		return logDirectory;
	}
	public void setLogDirectory(String logDirectory) {
		this.logDirectory = logDirectory;
	}

}
