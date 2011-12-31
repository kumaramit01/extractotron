package org.imirsel.extractotron.service;

import java.io.IOException;
import java.util.Set;

import org.imirsel.extractotron.model.SongCollection;
import org.imirsel.extractotron.model.Workspace;

public interface WorkspaceManager {
	public Workspace getWorkspaceForProject(Long projectId, String uuid);

	public String createInputFile(Workspace workspace,
			Set<SongCollection> collections) throws IOException;
}
