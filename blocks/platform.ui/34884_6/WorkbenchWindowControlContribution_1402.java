
package org.eclipse.ui.menus;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.services.IServiceLocator;

public abstract class UIElement {

	private IServiceLocator serviceLocator;

	protected UIElement(IServiceLocator serviceLocator)
			throws IllegalArgumentException {
		if (serviceLocator == null)
			throw new IllegalArgumentException();
		this.serviceLocator = serviceLocator;
	}

	public abstract void setText(String text);

	public abstract void setTooltip(String text);

	public abstract void setIcon(ImageDescriptor desc);

	public abstract void setDisabledIcon(ImageDescriptor desc);

	public abstract void setHoverIcon(ImageDescriptor desc);

	public abstract void setChecked(boolean checked);

	public final IServiceLocator getServiceLocator() {
		return serviceLocator;
	}

	public void setDropDownId(String id) {
	}
}
