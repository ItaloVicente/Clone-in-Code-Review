package org.eclipse.ui;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IEditorDescriptor extends IWorkbenchPartDescriptor {
    @Override
	public String getId();

    @Override
	public ImageDescriptor getImageDescriptor();

    @Override
	public String getLabel();

    public boolean isInternal();

    public boolean isOpenInPlace();

    public boolean isOpenExternal();

    public IEditorMatchingStrategy getEditorMatchingStrategy();
}
