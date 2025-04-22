package org.eclipse.egit.ui.internal.staging;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.core.IteratorService;
import org.eclipse.jgit.lib.Repository;


public class GitXStagedNode implements IAdaptable {
	public static enum State {
		ADDED,
		CHANGED,
		REMOVED,
		MISSING,
		MODIFIED,
		UNTRACKED,
		CONFLICTING
	}

	private Repository repository;

	private State state;

	private String path;

	public GitXStagedNode(Repository repository, State modified, String file) {
		this.repository = repository;
		this.state = modified;
		this.path = file;
	}

	public String getPath() {
		return path;
	}

	public State getState() {
		return state;
	}

	public Object getAdapter(Class adapter) {
		if (adapter == IResource.class) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

			IContainer findContainer = IteratorService.findContainer(root, repository.getWorkTree());
			IResource findMember = findContainer.findMember(path);

			return findMember;
		}
		return null;
	}
}
