package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public abstract class GitModelObject extends PlatformObject {

	private final GitModelObject parent;

	public static GitModelObject createRoot(GitSynchronizeData data)
			throws MissingObjectException, IOException {
		return new GitModelRepository(data);
	}

	public abstract GitModelObject[] getChildren();

	public abstract String getName();

	public abstract IProject[] getProjects();

	public abstract IPath getLocation();

	protected GitModelObject(GitModelObject parent) {
		this.parent = parent;
	}

	public GitModelObject getParent() {
		return parent;
	}

	public Repository getRepository() {
		return parent.getRepository();
	}

	protected TreeWalk createTreeWalk() {
		TreeWalk tw = new TreeWalk(getRepository());

		tw.reset();
		tw.setRecursive(false);
		tw.setFilter(TreeFilter.ANY_DIFF);

		return tw;
	}

}
