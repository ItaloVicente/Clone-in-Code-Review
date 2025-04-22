package org.eclipse.egit.core;

import org.eclipse.core.resources.IProject;

public interface IGitHook {

	public void postCommit(IProject project);

}
