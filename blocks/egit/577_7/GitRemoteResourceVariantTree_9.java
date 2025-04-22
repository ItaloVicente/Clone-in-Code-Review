package org.eclipse.egit.ui.internal.synchronize;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.team.core.TeamException;

class GitFolderResourceVariant extends GitResourceVariant {

	GitFolderResourceVariant(IResource resource) {
		super(resource);
	}

	IContainer getContainer() {
		return (IContainer) getResource();
	}

	public boolean isContainer() {
		return true;
	}

	public IStorage getStorage(IProgressMonitor monitor) throws TeamException {
		return null;
	}

	public String getContentIdentifier() {
		return getName();
	}

	@Override
	public int hashCode() {
		return getResource().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		GitFolderResourceVariant other = (GitFolderResourceVariant) obj;
		return getResource().equals(other.getResource());
	}

}
