package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.Category;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.DisplayItem;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.ShortcutItem;
import org.eclipse.ui.internal.dialogs.cpd.TreeManager.TreeItem;

class CategoryCheckProvider implements ICheckStateProvider {
	@Override
	public boolean isChecked(Object element) {
		Category category = (Category) element;

		if (category.getChildren().isEmpty()
				&& category.getContributionItems().isEmpty()) {
			return false;
		}

		for (TreeItem treeItem : category.getChildren()) {
			Category child = (Category) treeItem;
			if (isChecked(child)) {
				return true;
			}
		}

		for (ShortcutItem shortcutItem : category.getContributionItems()) {
			DisplayItem item = shortcutItem;
			if (item.getState()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isGrayed(Object element) {
		boolean hasChecked = false;
		boolean hasUnchecked = false;
		Category category = (Category) element;


		for (TreeItem treeItem : category.getChildren()) {
			Category child = (Category) treeItem;
			if (isGrayed(child)) {
				return true;
			}
			if (isChecked(child)) {
				hasChecked = true;
			} else {
				hasUnchecked = true;
			}
			if (hasChecked && hasUnchecked) {
				return true;
			}
		}

		for (ShortcutItem shortcutItem : category.getContributionItems()) {
			DisplayItem item = shortcutItem;
			if (item.getState()) {
				hasChecked = true;
			} else {
				hasUnchecked = true;
			}
			if (hasChecked && hasUnchecked) {
				return true;
			}
		}

		return false;
	}
}
