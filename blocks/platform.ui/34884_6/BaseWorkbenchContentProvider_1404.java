package org.eclipse.ui.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;

public class AdaptableList extends WorkbenchAdapter implements IAdaptable {

    protected List children = null;

    public AdaptableList() {
        children = new ArrayList();
    }

    public AdaptableList(int initialCapacity) {
        children = new ArrayList(initialCapacity);
    }

    public AdaptableList(IAdaptable[] newChildren) {
        this(newChildren.length);
        for (int i = 0; i < newChildren.length; i++) {
            children.add(newChildren[i]);
        }
    }

    public AdaptableList(Collection c) {
        this(c.size());
        children.addAll(c);
    }

    public AdaptableList add(IAdaptable adaptable) {
        Assert.isNotNull(adaptable);
        children.add(adaptable);
        return this;
    }

    public void remove(IAdaptable adaptable) {
        Assert.isNotNull(adaptable);
        children.remove(adaptable);
    }

    public int size() {
        return children.size();
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
        return children.toArray();
    }

    public Object[] getChildren() {
        return children.toArray();
    }
    
    public Object[] getTypedChildren(Class type) {
		return children.toArray((Object[]) Array.newInstance(type, children
				.size()));
	}

    @Override
	public String toString() {
        return children.toString();
    }
}
