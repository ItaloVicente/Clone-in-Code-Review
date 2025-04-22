package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class WizardPatternFilter extends PatternFilter {
	public WizardPatternFilter() {
		super();
	}

	@Override
	public boolean isElementSelectable(Object element) {
		return element instanceof WorkbenchWizardElement;
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		if (element instanceof WizardCollectionElement) {
			return false;
		}
		
		if (element instanceof WorkbenchWizardElement) {
			WorkbenchWizardElement desc = (WorkbenchWizardElement) element;
			String text = desc.getLabel();
			if (wordMatches(text)) {
				return true;
			}

			String[] keywordLabels = desc.getKeywordLabels();
			for (int i = 0; i < keywordLabels.length; i++) {
				if (wordMatches(keywordLabels[i]))
					return true;
			}
		}
		return false;
	}

	@Override
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		ArrayList<Object> result = new ArrayList<Object>();

		for (Object elem : super.filter(viewer, parent, elements)) {
			if (elem instanceof WizardCollectionElement) {
				Object wizardCollection = WizardCollectionElement.filter(viewer, this,
						(WizardCollectionElement) elem);
				if (wizardCollection != null) {
					result.add(wizardCollection);
				}
			} else {
				result.add(elem);
			}
		}

		return result.toArray();
	}
}
