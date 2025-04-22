package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.jgit.errors.MissingObjectException;

public abstract class GitModelObject extends PlatformObject {

	private final GitModelObject parent;

	public static GitModelObject createRoot(GitSynchronizeData data)
			throws MissingObjectException, IOException {
		return new GitModelRepository(data);
	}

	public GitModelObject(GitModelObject parent) {
		this.parent = parent;
	}

	public GitModelObject getParent() {
		return parent;
	}

	public abstract int repositoryHashCode();

	public abstract GitModelObject[] getChildren();

	public abstract String getName();

	public abstract int getKind();

	public abstract IPath getLocation();

	public abstract boolean isContainer();

	public abstract void dispose();

}
