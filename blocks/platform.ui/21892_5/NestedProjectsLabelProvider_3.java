package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.IWorkingSet;

public class NestedProjectsFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFolder) {
			if (NestedProjectManager.isShownAsProject((IFolder)element)) {
				return false;
			}
		}
		if (element instanceof IProject) {
			if (parentElement != null && (parentElement instanceof IWorkspaceRoot || parentElement instanceof IWorkingSet)) {
				if (NestedProjectManager.isShownAsNested((IProject)element)) {
					return false;
				}
			}
		}
		return true;
	}

}
