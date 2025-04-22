package org.eclipse.egit.gitflow.ui.internal.dialogs;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeColumn;

public class BranchComparator extends ViewerComparator {
	private TreeColumn currentColumn;

	private static final int DESCENDING = 1;

	private int direction = DESCENDING;

	private ColumnLabelProvider labelProvider;

	public int getDirection() {
		return direction == 1 ? SWT.DOWN : SWT.UP;
	}

	public void setColumn(TreeColumn column, ColumnLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
		if (column.equals(currentColumn)) {
			flipSortDirection();
		} else {
			currentColumn = column;
			direction = DESCENDING;
		}
	}

	private void flipSortDirection() {
		direction = -direction;
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int rc = 0;

		if (direction == DESCENDING) {
			rc = super.compare(viewer, labelProvider.getText(e1), labelProvider.getText(e2));
		} else {
			rc = super.compare(viewer, labelProvider.getText(e2), labelProvider.getText(e1));
		}

		return rc;
	}
}
