package org.eclipse.egit.ui.internal.decorators;

import org.eclipse.core.resources.IResource;

class DecoratableResource implements IDecoratableResource {

	IResource resource = null;

	String repositoryName = null;

	String branch = null;

	boolean tracked = false;

	boolean ignored = false;

	boolean dirty = false;

	Staged staged = Staged.NOT_STAGED;

	boolean conflicts = false;

	boolean assumeValid = false;

	DecoratableResource(IResource resource) {
		this.resource = resource;
	}

	public int getType() {
		return resource != null ? resource.getType() : 0;
	}

	public String getName() {
		return resource != null ? resource.getName() : null;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public String getBranch() {
		return branch;
	}

	public boolean isTracked() {
		return tracked;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public boolean isDirty() {
		return dirty;
	}

	public Staged staged() {
		return staged;
	}

	public boolean hasConflicts() {
		return conflicts;
	}

	public boolean isAssumeValid() {
		return assumeValid;
	}
}
