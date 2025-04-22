package org.eclipse.egit.ui.internal.synchronize.model;

public abstract class GitModelObjectContainer extends GitModelObject {

	public GitModelObjectContainer(GitModelObjectContainer parent) {
		super(parent);
	}

	@Override
	public boolean isContainer() {
		return true;
	}

}
