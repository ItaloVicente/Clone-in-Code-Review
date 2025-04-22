package org.eclipse.ui.internal.navigator.workingsets;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class WorkingSetsLabelProvider implements ILabelProvider {

	private WorkbenchLabelProvider labelProvider = new WorkbenchLabelProvider();

	@Override
	public Image getImage(Object element) {
		if (element instanceof IWorkingSet)
			return labelProvider.getImage(element);
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IWorkingSet)
			return ((IWorkingSet) element).getLabel();
		return null;
	}

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

}
