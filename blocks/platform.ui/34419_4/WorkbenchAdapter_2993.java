package org.eclipse.ui.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

public abstract class WorkbenchAdapter implements IWorkbenchAdapter,
        IWorkbenchAdapter2, IWorkbenchAdapter3 {
    protected static final Object[] NO_CHILDREN = new Object[0];

    @Override
	public Object[] getChildren(Object object) {
        return NO_CHILDREN;
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object object) {
        return null;
    }

    @Override
	public String getLabel(Object object) {
        return object == null ? "" : object.toString(); //$NON-NLS-1$
    }

    @Override
	public Object getParent(Object object) {
        return null;
    }

    @Override
	public RGB getBackground(Object element) {
        return null;
    }

    @Override
	public RGB getForeground(Object element) {
        return null;
    }

    @Override
	public FontData getFont(Object element) {
        return null;
    }

	@Override
	public StyledString getStyledText(Object object) {
		return new StyledString(getLabel(object));
    }
}
