package org.eclipse.ui.model;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IWorkbenchAdapter {
    public Object[] getChildren(Object o);

    public ImageDescriptor getImageDescriptor(Object object);

    public String getLabel(Object o);

    public Object getParent(Object o);
}
