package org.eclipse.egit.ui.internal;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class TreeColumnPatternFilter extends PatternFilter {

	public TreeColumnPatternFilter() {
		setIncludeLeadingWildcard(true);
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		TreeViewer treeViewer = (TreeViewer) viewer;
		int numberOfColumns = treeViewer.getTree().getColumnCount();
		for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
			ColumnLabelProvider labelProvider = (ColumnLabelProvider) treeViewer
					.getLabelProvider(columnIndex);
			String labelText = labelProvider.getText(element);
			if (wordMatches(labelText)) {
				return true;
			}
		}
		return false;
	}

}
