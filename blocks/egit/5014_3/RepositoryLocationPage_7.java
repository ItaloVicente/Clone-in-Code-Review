package org.eclipse.egit.ui.internal.clone;

import org.eclipse.egit.ui.internal.clone.GitCloneSourceProviderExtension.CloneSourceProvider;
import org.eclipse.egit.ui.internal.provisional.wizards.RepositoryServerInfo;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

class RepositoryLocationLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof CloneSourceProvider)
			return ((CloneSourceProvider) element).getLabel();
		else if (element instanceof RepositoryServerInfo)
			return ((RepositoryServerInfo) element).getLabel();
		return null;
	}

	@Override
	public Image getImage(Object element) {
		return super.getImage(element);
	}
}
