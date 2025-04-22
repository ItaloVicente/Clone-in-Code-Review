package org.eclipse.egit.ui.internal.staging;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.egit.core.IteratorService;
import org.eclipse.jgit.lib.Repository;


public class StagedNode implements IAdaptable {
	public static enum State {
		ADDED(true),
		CHANGED(true),
		REMOVED(true),
		MISSING(true),
		MODIFIED(false),
		UNTRACKED(false),
		CONFLICTING(false);

		private boolean staged;

		private State(boolean staged) {
			this.staged = staged;
		}

		public boolean isStaged() {
			return staged;
		}
	}

	private Repository repository;

	private State state;

	private String path;

	public StagedNode(Repository repository, State modified, String file) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StagedNode other = (StagedNode) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (state != other.state)
			return false;
		return true;
	}
}
