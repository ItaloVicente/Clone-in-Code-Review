package org.eclipse.egit.ui.internal.repository.tree;

import java.text.Collator;

import org.eclipse.jface.viewers.Viewer;

public class RepositoriesViewSorter extends
		org.eclipse.jface.viewers.ViewerSorter {

	public RepositoriesViewSorter() {
		this(null);
	}

	public RepositoriesViewSorter(Collator collator) {
		super(collator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int category(Object element) {
		if (element instanceof RepositoryTreeNode) {
			RepositoryTreeNode<? extends Object> node = (RepositoryTreeNode<? extends Object>) element;
			if (node.getType() == RepositoryTreeNodeType.BRANCHHIERARCHY)
				return RepositoryTreeNodeType.REF.ordinal();
			return node.getType().ordinal();
		}
		return super.category(element);
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (e1 instanceof RepositoryTreeNode
				&& e2 instanceof RepositoryTreeNode) {
			RepositoryTreeNode node1 = (RepositoryTreeNode) e1;
			RepositoryTreeNode node2 = (RepositoryTreeNode) e2;
			return node1.compareTo(node2);
		} else {
			return super.compare(viewer, e1, e2);
		}
	}
}
