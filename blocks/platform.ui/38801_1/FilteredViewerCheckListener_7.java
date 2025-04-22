package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.DisplayItem;

class FilteredViewerCheckListener implements
		ICheckStateListener {
	private ITreeContentProvider contentProvider;
	private ViewerFilter filter;

	public FilteredViewerCheckListener(
			ITreeContentProvider contentProvider, ViewerFilter filter) {
		this.contentProvider = contentProvider;
		this.filter = filter;
	}

	@Override
	public void checkStateChanged(CheckStateChangedEvent event) {
		setAllLeafs((DisplayItem) event.getElement(), event
				.getChecked(), contentProvider, filter);
	}

	static void setAllLeafs(DisplayItem item, boolean value, ITreeContentProvider provider, ViewerFilter filter) {
		Object[] children = provider.getChildren(item);
		boolean isLeaf = true;

		for (Object element : children) {
			isLeaf = false;
			if (filter.select(null, null, element)) {
				DisplayItem child = (DisplayItem) element;
				setAllLeafs(child, value, provider, filter);
			}
		}

		if (isLeaf) {
			item.setCheckState(value);
		}
	}
}
