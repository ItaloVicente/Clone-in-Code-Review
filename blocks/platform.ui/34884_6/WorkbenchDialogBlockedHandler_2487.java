package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.wizards.IWizardDescriptor;

public class WizardTagFilter extends ViewerFilter {

	private String [] myTags;
	
	public WizardTagFilter(String [] tags) {
		myTags = tags;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IWizardDescriptor) {
			IWizardDescriptor desc = (IWizardDescriptor)element;
			String [] tags = desc.getTags();
			for (int i = 0; i < tags.length; i++) {
				for (int j = 0; j < myTags.length; j++) {
					if (tags[i].equals(myTags[j])) {
						return true;
					}
				}
			}
			return false;
		}
        Object[] children = ((ITreeContentProvider) ((AbstractTreeViewer) viewer)
                .getContentProvider()).getChildren(element);
        if (children.length > 0) {
			return filter(viewer, element, children).length > 0;
		}

		return false;
	}
}
