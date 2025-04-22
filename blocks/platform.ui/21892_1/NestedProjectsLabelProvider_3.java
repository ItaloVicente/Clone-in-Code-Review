package org.eclipse.ui.internal.navigator.resources.nestedProjects;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class NestedProjectsFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFolder) {
			if (NestedProjectUtils.isShownAsProject((IFolder)element)) {
				return false;
			}
		}
		return true;
	}

}
