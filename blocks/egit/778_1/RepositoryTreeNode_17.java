package org.eclipse.egit.ui.internal.repository.tree;

import java.text.Collator;

public class RepositoriesViewSorter extends
		org.eclipse.jface.viewers.ViewerSorter {

	public RepositoriesViewSorter() {
	}

	public RepositoriesViewSorter(Collator collator) {
		super(collator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int category(Object element) {
		if (element instanceof RepositoryTreeNode) {
			RepositoryTreeNode<? extends Object> node = (RepositoryTreeNode<? extends Object>) element;
			return node.getType().ordinal();
		}
		return super.category(element);
	}

}
