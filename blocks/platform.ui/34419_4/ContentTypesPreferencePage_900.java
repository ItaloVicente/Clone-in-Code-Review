package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.activities.WorkbenchActivityHelper;

public class CapabilityFilter extends ViewerFilter {

	public CapabilityFilter() {
		super();
		
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return ! WorkbenchActivityHelper.filterItem(element);
	}

}
