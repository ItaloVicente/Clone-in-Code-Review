package org.eclipse.ui.internal.dialogs.cpd;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.ActionSet;
import org.eclipse.ui.internal.dialogs.cpd.CustomizePerspectiveDialog.DisplayItem;

class ActionSetFilter extends ViewerFilter {
	private ActionSet actionSet;

	public void setActionSet(ActionSet actionSet) {
		this.actionSet = actionSet;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement,
			Object element) {
		if (!(element instanceof DisplayItem)) {
			return false;
		}
		if (actionSet == null) {
			return false;
		}
		return CustomizePerspectiveDialog.includeInSetStructure((DisplayItem) element, actionSet);
	}
}
