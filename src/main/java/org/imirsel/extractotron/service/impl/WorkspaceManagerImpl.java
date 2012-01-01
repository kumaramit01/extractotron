package org.imirsel.extractotron.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.compass.core.util.Assert;
import org.imirsel.extractotron.model.Song;
import org.imirsel.extractotron.model.SongCollection;
import org.imirsel.extractotron.model.Workspace;
import org.imirsel.extractotron.service.WorkspaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service("workspaceManager")
public class WorkspaceManagerImpl implements WorkspaceManager {
	
	private String parentDirectory;

	@Autowired
	private Properties workspaceProperties;
	

	@PostConstruct
	public void init(){
		parentDirectory=workspaceProperties.getProperty("location");
		Assert.isTrue(parentDirectory!=null);
		File file = new File(parentDirectory);
		Assert.isTrue(file.exists());
		Assert.isTrue(file.isDirectory());
		Assert.isTrue(file.canRead());
		Assert.isTrue(file.canWrite());
	}

	public Workspace getWorkspaceForProject(Long projectId, String execution_id) {
		Workspace workspace = new Workspace();
		File homeDirectory = new File(parentDirectory,projectId+"");
		homeDirectory.mkdir();
		
		File executionContextDirectory = new File(homeDirectory,execution_id);
		executionContextDirectory.mkdir();
		
		File logDirectory = new File(executionContextDirectory,"log");
		logDirectory.mkdir();
		
		File outputFile = new File(executionContextDirectory,"output");
		File inputFile = new File(executionContextDirectory,"input");
		
		File errFile = new File(logDirectory,"err.txt");
		
		workspace.setOutputFile(outputFile.getAbsolutePath());
		workspace.setInputFile(inputFile.getAbsolutePath());
		workspace.setDirectory(executionContextDirectory.getAbsolutePath());
		workspace.setLogDirectory(logDirectory.getAbsolutePath());
		workspace.setErrFile(errFile.getAbsolutePath());
		return workspace;
	}

	public String createInputFile(Workspace workspace,
			Set<SongCollection> collections) throws IOException {
		File file = new File(workspace.getInputFile());
		BufferedWriter writer  = new BufferedWriter(new FileWriter(file));
		
		for(SongCollection collection:collections){
			for(Song song:collection.getSongs()){
				String loc=song.getLocation();
				writer.write(loc+"\n");
			}
		}
		writer.flush();
		return workspace.getInputFile();
	}

}
