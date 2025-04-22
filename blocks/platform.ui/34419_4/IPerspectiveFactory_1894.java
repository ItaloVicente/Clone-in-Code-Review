package org.eclipse.ui;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IPerspectiveDescriptor {
    public String getDescription();

    public String getId();

    public ImageDescriptor getImageDescriptor();

    public String getLabel();
}
