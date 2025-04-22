package org.eclipse.ui.examples.readmetool;

import java.util.Vector;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.views.properties.IPropertySource;

public class MarkElement implements IWorkbenchAdapter, IAdaptable {
    private String headingName;

    private IAdaptable parent;

    private int offset;

    private int numberOfLines;

    private int length;

    private Vector children;

    public MarkElement(IAdaptable parent, String heading, int offset, int length) {
        this.parent = parent;
        if (parent instanceof MarkElement) {
            ((MarkElement) parent).addChild(this);
        }
        this.headingName = heading;
        this.offset = offset;
        this.length = length;
    }

    private void addChild(MarkElement child) {
        if (children == null) {
            children = new Vector();
        }
        children.add(child);
    }

    public Object getAdapter(Class adapter) {
        if (adapter == IWorkbenchAdapter.class)
            return this;
        if (adapter == IPropertySource.class)
            return new MarkElementProperties(this);
        return null;
    }

    public Object[] getChildren(Object object) {
        if (children != null) {
            return children.toArray();
        }
        return new Object[0];
    }

    public ImageDescriptor getImageDescriptor(Object object) {
        IWorkbenchAdapter parentElement = (IWorkbenchAdapter) parent
                .getAdapter(IWorkbenchAdapter.class);
        if (parentElement != null) {
            return parentElement.getImageDescriptor(object);
        }
        return null;
    }

    public String getLabel(Object o) {
        return headingName;
    }

    public int getLength() {
        return length;
    }

    public int getNumberOfLines() {
        return numberOfLines;
    }

    public Object getParent(Object o) {
        return null;
    }

    public int getStart() {
        return offset;
    }

    public void setNumberOfLines(int newNumberOfLines) {
        numberOfLines = newNumberOfLines;
    }
}
