
package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class WizardCollectionElementFilter {
	public static WizardCollectionElement filter(Viewer viewer, ViewerFilter viewerFilter,
			WizardCollectionElement inputCollection) {
		WizardCollectionElement modifiedCollection = null;

		for (Object child : inputCollection.getWizardAdaptableList().getChildren()) {
			if (!viewerFilter.select(viewer, inputCollection, child)) {
				if (modifiedCollection == null) {
					modifiedCollection = (WizardCollectionElement) inputCollection.clone();
				}
				modifiedCollection.getWizardAdaptableList().remove((IAdaptable) child);
			}
		}

		if (modifiedCollection == null) {
			return inputCollection;
		}
		if (modifiedCollection.getWizardAdaptableList().size() == 0) {
			return null;
		}
		return modifiedCollection;
	}
}
