package org.eclipse.ui.examples.readmetool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.jface.resource.ImageDescriptor;

import org.eclipse.ui.model.IWorkbenchAdapter;

public class AdaptableList implements IWorkbenchAdapter, IAdaptable {
    protected List<IAdaptable> children = new ArrayList<>();

    public AdaptableList() {
    }

    public AdaptableList(IAdaptable[] newChildren) {
        for (int i = 0; i < newChildren.length; i++) {
            children.add(newChildren[i]);
        }
    }

    public AdaptableList add(Iterator<IAdaptable> iterator) {
        while (iterator.hasNext()) {
            add(iterator.next());
        }
        return this;
    }

    public AdaptableList add(IAdaptable adaptable) {
        children.add(adaptable);
        return this;
    }

    @SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
        if (adapter == IWorkbenchAdapter.class)
            return (T)this;
        return null;
    }

    public Object[] getChildren() {
        return children.toArray();
    }

    @Override
	public Object[] getChildren(Object o) {
        return children.toArray();
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

    public void remove(IAdaptable adaptable) {
        children.remove(adaptable);
    }

    public int size() {
        return children.size();
    }
}
