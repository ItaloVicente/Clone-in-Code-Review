package org.eclipse.ui.model;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.internal.util.Util;

public class BaseWorkbenchContentProvider implements ITreeContentProvider {

    public BaseWorkbenchContentProvider() {
        super();
    }

    @Override
	public void dispose() {
    }

    protected IWorkbenchAdapter getAdapter(Object element) {
        return (IWorkbenchAdapter)Util.getAdapter(element, IWorkbenchAdapter.class);
    }

    @Override
	public Object[] getChildren(Object element) {
        IWorkbenchAdapter adapter = getAdapter(element);
        if (adapter != null) {
            return adapter.getChildren(element);
        }
        return new Object[0];
    }

    @Override
	public Object[] getElements(Object element) {
        return getChildren(element);
    }

    @Override
	public Object getParent(Object element) {
        IWorkbenchAdapter adapter = getAdapter(element);
        if (adapter != null) {
            return adapter.getParent(element);
        }
        return null;
    }

    @Override
	public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }

}
