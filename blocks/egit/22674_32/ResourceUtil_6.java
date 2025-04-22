package org.eclipse.egit.core.internal.storage;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.storage.GitBlobStorage;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class WorkspaceGitBlobStorage extends GitBlobStorage {

	private final IPath workspacePath;

	public WorkspaceGitBlobStorage(Repository repository, String path, IPath workspacePath, ObjectId blob) {
		super(repository, path, blob);
		this.workspacePath = workspacePath;
	}

	public IPath getWorkspacePath() {
		return workspacePath;
	}
}
