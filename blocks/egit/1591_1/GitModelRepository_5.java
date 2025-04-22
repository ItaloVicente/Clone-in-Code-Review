package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitModelCachedBlob extends GitModelBlob {

	private final String path;

	protected GitModelCachedBlob(GitModelObjectContainer parent,
			RevCommit baseCommit, ObjectId repoId, ObjectId cached, String path)
			throws IOException {
		super(parent, baseCommit, repoId, repoId, cached, path);
		this.path = path;
	}

	@Override
	public String getName() {
		return path;
	}

	@Override
	public IProject[] getProjects() {
		return getParent().getProjects();
	}

	@Override
	public boolean isContainer() {
		return false;
	}

}
