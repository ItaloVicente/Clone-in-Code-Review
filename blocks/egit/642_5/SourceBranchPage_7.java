package org.eclipse.egit.ui.internal.clone;

import org.eclipse.core.resources.IProject;

interface ProjectCreator {

	public void importProjects();

	public IProject[] getAddedProjects();

}
