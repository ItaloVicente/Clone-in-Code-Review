package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.dialogs.cpd.TreeManager.TreeItem;

class FilteredTreeCheckProvider implements ICheckStateProvider {
	private ITreeContentProvider contentProvider;
	private ViewerFilter filter;

	public FilteredTreeCheckProvider(ITreeContentProvider contentProvider,
			ViewerFilter filter) {
		this.contentProvider = contentProvider;
		this.filter = filter;
	}

	@Override
	public boolean isChecked(Object element) {
		TreeItem treeItem = (TreeItem) element;
		return getLeafStates(treeItem, contentProvider, filter) != TreeManager.CHECKSTATE_UNCHECKED;
	}

	@Override
	public boolean isGrayed(Object element) {
		TreeItem treeItem = (TreeItem) element;
		return getLeafStates(treeItem, contentProvider, filter) == TreeManager.CHECKSTATE_GRAY;
	}

	static int getLeafStates(TreeItem item, ITreeContentProvider provider, ViewerFilter filter) {
		Object[] children = provider.getChildren(item);

		boolean checkedFound = false;
		boolean uncheckedFound = false;

		for (Object element : children) {
			if (filter.select(null, null, element)) {
				TreeItem child = (TreeItem) element;
				switch (getLeafStates(child, provider, filter)) {
				case TreeManager.CHECKSTATE_CHECKED: {
					checkedFound = true;
					break;
				}
				case TreeManager.CHECKSTATE_GRAY: {
					checkedFound = uncheckedFound = true;
					break;
				}
				case TreeManager.CHECKSTATE_UNCHECKED: {
					uncheckedFound = true;
					break;
				}
				}
				if (checkedFound && uncheckedFound) {
					return TreeManager.CHECKSTATE_GRAY;
				}
			}
		}

		if (!checkedFound && !uncheckedFound) {
			return item.getState() ? TreeManager.CHECKSTATE_CHECKED : TreeManager.CHECKSTATE_UNCHECKED;
		}
		return checkedFound ? TreeManager.CHECKSTATE_CHECKED : TreeManager.CHECKSTATE_UNCHECKED;
	}
}
