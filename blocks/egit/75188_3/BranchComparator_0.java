package org.eclipse.egit.gitflow.ui.internal.dialogs;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TreeColumn;

public class BranchComparator extends ViewerComparator {
	private TreeColumn currentColumn;

	private static final int DESCENDING = SWT.DOWN;

	private static final int ASCENDING = SWT.UP;

	private int direction = DESCENDING;

	private ColumnLabelProvider labelProvider;

	public int getDirection() {
		return direction;
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
		direction = (direction == DESCENDING) ? ASCENDING : DESCENDING;
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int rc = 0;

		String firstCell = labelProvider.getText(e1).toLowerCase();
		String secondCell = labelProvider.getText(e2).toLowerCase();
		if (direction == DESCENDING) {
			rc = secondCell.compareTo(firstCell);
		} else {
			rc = firstCell.compareTo(secondCell);
		}

		return rc;
	}
}
