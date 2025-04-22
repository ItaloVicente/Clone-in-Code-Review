package org.eclipse.egit.core.internal.storage;

import org.eclipse.jgit.lib.Repository;

public interface OpenWorkspaceVersionEnabled {

	public Repository getRepository();

	public String getGitPath();

}
