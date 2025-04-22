package org.eclipse.egit.ui.internal.repository.tree.filter;

import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNode;
import org.eclipse.egit.ui.internal.repository.tree.RepositoryTreeNodeType;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

class NodeByTypeFilter extends ViewerFilter {

	private RepositoryTreeNodeType typeToHide;

	NodeByTypeFilter(RepositoryTreeNodeType typeToHide) {
		this.typeToHide = typeToHide;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof RepositoryTreeNode<?>) {
			RepositoryTreeNodeType type = ((RepositoryTreeNode) element)
					.getType();
			if (type == typeToHide) {
				return false;
			}
		}
		return true;
	}
}
