package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;

class GitModelWorkingFile extends GitModelBlob {

	public GitModelWorkingFile(GitModelObjectContainer parent,
			RevCommit commit, ObjectId repoId, String name) throws IOException {
		super(parent, commit, repoId, repoId, null, name);
	}

	@Override
	public ITypedElement getLeft() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFileForLocation(getLocation());

		return new LocalResourceTypedElement(file);
	}

}
