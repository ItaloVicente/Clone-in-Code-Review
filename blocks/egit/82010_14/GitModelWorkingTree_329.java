package org.eclipse.egit.ui.internal.synchronize.model;

import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.synchronize.GitCommitsModelCache.Change;
import org.eclipse.jgit.lib.Repository;

public class GitModelWorkingFile extends GitModelBlob {

	GitModelWorkingFile(GitModelObjectContainer parent, Repository repo,
			Change change, IPath path) {
		super(parent, repo, change, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj == null)
			return false;

		if (obj.getClass() != getClass())
			return false;

		GitModelWorkingFile objBlob = (GitModelWorkingFile) obj;

		return hashCode() == objBlob.hashCode();
	}

	@Override
	public int hashCode() {
		return super.hashCode() + 41;
	}

}
