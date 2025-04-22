package org.eclipse.ui.navigator.resources;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IWorkingSet;

public class OpenProjectsWorkingSetFilter extends ViewerFilter {

    @Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		IWorkingSet resource = Adapters.adapt(element, IWorkingSet.class);
		if (resource == null) {
			return true;
		}
		for (IAdaptable subElement : resource.getElements()) {
			IProject project = Adapters.adapt(subElement, IProject.class);
			if (project != null && project.isOpen()) {
				return true;
			}
		}
		return false;
    }
}
