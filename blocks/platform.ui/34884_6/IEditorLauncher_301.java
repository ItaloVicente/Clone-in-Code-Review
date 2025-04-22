package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;

public interface IEditorInput extends IAdaptable {
	public boolean exists();

	public ImageDescriptor getImageDescriptor();

	public String getName();

	public IPersistableElement getPersistable();

	public String getToolTipText();
}
