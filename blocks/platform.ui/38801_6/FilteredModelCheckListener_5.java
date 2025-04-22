package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.ui.internal.dialogs.cpd.TreeManager.CheckListener;
import org.eclipse.ui.internal.dialogs.cpd.TreeManager.TreeItem;

final class FilteredModelCheckListener implements CheckListener {
	private final ActionSetFilter filter;
	private final StructuredViewer viewer;

	FilteredModelCheckListener(ActionSetFilter filter,
			StructuredViewer viewer) {
		this.filter = filter;
		this.viewer = viewer;
	}

	@Override
	public void checkChanged(TreeItem changedItem) {
		TreeItem item = changedItem;
		boolean update = false;

		while (item != null) {
			update = update || filter.select(null, null, item);
			if (update) {
				viewer.update(item, null);
			}
			item = item.getParent();
		}
	}
}
