package org.eclipse.ui;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IWorkbenchPartDescriptor {
    public String getId();

    public ImageDescriptor getImageDescriptor();

    public String getLabel();
}
