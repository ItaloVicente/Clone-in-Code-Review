package org.eclipse.ui.internal.dialogs;

import org.eclipse.e4.ui.model.LocalizationHelper;
import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class ViewPatternFilter extends PatternFilter {


	@Override
	public boolean isElementSelectable(Object element) {
		return element instanceof MPartDescriptor;
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		if (element instanceof String) {
			return false;
		}

		String text = null;
		if (element instanceof MPartDescriptor) {
			MPartDescriptor desc = (MPartDescriptor) element;
			text = LocalizationHelper.getLocalized(desc.getLabel(), desc);
			if (wordMatches(text)) {
				return true;
			}
		}

		return false;
	}
}
