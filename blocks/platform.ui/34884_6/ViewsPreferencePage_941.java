package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;

public class ViewPatternFilter extends PatternFilter {

	public ViewPatternFilter() {
		super();
	}

	@Override
	public boolean isElementSelectable(Object element) {
		return element instanceof IViewDescriptor;
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		if (element instanceof IViewCategory) {
			return false;
		}

		String text = null;
		if (element instanceof IViewDescriptor) {
			IViewDescriptor desc = (IViewDescriptor) element;
			text = desc.getLabel();
			if (wordMatches(text)) {
				return true;
			}
		}

		return false;
	}
}
