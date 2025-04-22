package org.eclipse.ui.internal.navigator.nestor;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class NestedProjectsLabelProvider implements ILabelProvider {

	private WorkbenchLabelProvider labelProvider = new WorkbenchLabelProvider();
	
	@Override
	public void addListener(ILabelProviderListener listener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
	}

	@Override
	public Image getImage(Object element) {
		return null;
	}

	@Override
	public String getText(Object element) {
		if (! (element instanceof IProject)) {
			return null;
		}
		IProject project = (IProject)element;
		if (!project.getLocation().lastSegment().equals(project.getName())) {
			return labelProvider.getText(element) + " (in " + project.getLocation().lastSegment() + ")"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		return null;
	}
}
