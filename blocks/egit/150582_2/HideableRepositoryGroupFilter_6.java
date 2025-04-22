package org.eclipse.egit.ui.internal.repository.tree.filter;

import org.eclipse.egit.ui.internal.repository.tree.RepositoryGroupNode;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class HideableRepositoryGroupFilter extends ViewerFilter {

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof RepositoryGroupNode) {
			if (((RepositoryGroupNode) element).getGroup().isHideable()) {
				return false;
			}
		}
		return true;
	}
}
