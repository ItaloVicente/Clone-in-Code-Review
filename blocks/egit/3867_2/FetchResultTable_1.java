package org.eclipse.egit.ui.internal;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class WorkbenchStyledLabelProvider implements IStyledLabelProvider {

	protected final WorkbenchLabelProvider workbenchLabelProvider = new WorkbenchLabelProvider();

	public void removeListener(ILabelProviderListener listener) {
	}

	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	public void dispose() {
		workbenchLabelProvider.dispose();
	}

	public void addListener(ILabelProviderListener listener) {
	}

	public StyledString getStyledText(Object element) {
		return new StyledString(workbenchLabelProvider.getText(element));
	}

	public Image getImage(Object element) {
		return workbenchLabelProvider.getImage(element);
	}
}
