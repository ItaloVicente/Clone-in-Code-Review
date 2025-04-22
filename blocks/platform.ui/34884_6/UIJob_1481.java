package org.eclipse.ui.progress;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.progress.ProgressMessages;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class PendingUpdateAdapter implements IWorkbenchAdapter, IAdaptable {

    private boolean removed = false;

    protected boolean isRemoved() {
        return removed;
    }

    protected void setRemoved(boolean removedValue) {
        this.removed = removedValue;
    }

    public PendingUpdateAdapter() {
    }

    @Override
	public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class) {
			return this;
		}
        return null;
    }

    @Override
	public Object[] getChildren(Object o) {
        return new Object[0];
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object object) {
        return null;
    }

    @Override
	public String getLabel(Object o) {
        return ProgressMessages.PendingUpdateAdapter_PendingLabel;
    }

    @Override
	public Object getParent(Object o) {
        return null;
    }
    
    @Override
	public String toString() {
    	return getLabel(null);
    }
}
