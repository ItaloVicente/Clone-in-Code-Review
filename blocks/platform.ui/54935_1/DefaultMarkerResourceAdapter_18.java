package org.eclipse.ui.views.markers.internal;

import java.util.Comparator;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;

public class CategoryComparator extends ViewerComparator implements Comparator {
	TableComparator innerSorter;

	IField categoryField;

	private final String TAG_FIELD = "categoryField"; //$NON-NLS-1$

	CategoryComparator(TableComparator sorter) {
		innerSorter = sorter;
	}

	int compare(Object obj1, Object obj2, int depth, boolean continueSearching) {

		if (obj1 == null || obj2 == null || !(obj1 instanceof MarkerNode)
				|| !(obj2 instanceof MarkerNode)) {
			return 0;
		}

		MarkerNode marker1;
		MarkerNode marker2;

		marker1 = (MarkerNode) obj1;
		marker2 = (MarkerNode) obj2;

		if (categoryField == null) {
			return innerSorter.compare(marker1, marker2, depth,
					continueSearching);
		}

		int result = categoryField.compare(marker1, marker2);
		if (continueSearching && result == 0) {
			return innerSorter.compare(marker1, marker2, 0, continueSearching);
		}
		return result;
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		return compare(e1, e2, 0, true);
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		return compare(arg0, arg1, 0, true);
	}

	public IField getCategoryField() {
		return categoryField;
	}

	public void setCategoryField(IField field) {
		this.categoryField = field;
	}

	public void setTableSorter(TableComparator sorter2) {
		innerSorter = sorter2;

	}

	public void saveState(IDialogSettings dialogSettings) {
		if (dialogSettings == null) {
			return;
		}

		IDialogSettings settings = dialogSettings
				.getSection(TableComparator.TAG_DIALOG_SECTION);
		if (settings == null) {
			settings = dialogSettings
					.addNewSection(TableComparator.TAG_DIALOG_SECTION);
		}

		String description = Util.EMPTY_STRING;
		if (categoryField != null) {
			description = categoryField.getDescription();
		}

		settings.put(TAG_FIELD, description);

	}

	public void restoreState(IDialogSettings dialogSettings, ProblemView view) {
		if (dialogSettings == null) {
			selectDefaultGrouping(view);
			return;
		}

		IDialogSettings settings = dialogSettings
				.getSection(TableComparator.TAG_DIALOG_SECTION);
		if (settings == null) {
			selectDefaultGrouping(view);
			return;
		}

		String description = settings.get(TAG_FIELD);
		view.selectCategory(description, this);
	}

	private void selectDefaultGrouping(ProblemView view) {
		view.selectCategoryField(MarkerSupportRegistry.getInstance()
				.getDefaultGroupField(), this);
	}
}
