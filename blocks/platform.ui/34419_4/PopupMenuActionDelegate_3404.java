package org.eclipse.ui.examples.propertysheet;

import java.util.Vector;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IBasicPropertyConstants;

import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public abstract class OrganizationElement implements IAdaptable,
        IPropertySource, IWorkbenchAdapter {
    private GroupElement parent;

    private String name;

    private ImageDescriptor imageDescriptor;

    private static Vector<PropertyDescriptor> descriptors;
    static {
        descriptors = new Vector<>();
        PropertyDescriptor name = new TextPropertyDescriptor(
                IBasicPropertyConstants.P_TEXT, MessageUtil.getString("name")); //$NON-NLS-1$
        descriptors.addElement(name);
    }

    OrganizationElement(String name, GroupElement parent) {
        this.name = name;
        this.parent = parent;
    }

    public void delete() {
        parent.delete(this);
    }

    @SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
        if (adapter == IPropertySource.class) {
            return (T)this;
        }
        if (adapter == IWorkbenchAdapter.class) {
            return (T)this;
        }
        return null;
    }

    static Vector<PropertyDescriptor> getDescriptors() {
        return descriptors;
    }

    @Override
	public Object getEditableValue() {
        return this;
    }

    @Override
	public ImageDescriptor getImageDescriptor(Object object) {
        return imageDescriptor;
    }

    @Override
	public String getLabel(Object o) {
        return getName();
    }

    String getName() {
        return name;
    }

    @Override
	public Object getParent(Object o) {
        return parent;
    }

    @Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    @Override
	public Object getPropertyValue(Object propKey) {
        if (propKey.equals(IBasicPropertyConstants.P_TEXT))
            return getName();
        return null;
    }

    public boolean isGroup() {
        return false;
    }

    @Override
	public boolean isPropertySet(Object property) {
        return false;
    }

    public boolean isUser() {
        return false;
    }

    @Override
	public void resetPropertyValue(Object property) {
    }

    void setImageDescriptor(ImageDescriptor desc) {
        imageDescriptor = desc;
    }

    void setName(String newName) {
        name = newName;
    }

    void setParent(GroupElement newParent) {
        parent = newParent;
    }

    @Override
	public void setPropertyValue(Object name, Object value) {
        if (name.equals(IBasicPropertyConstants.P_TEXT)) {
            setName((String) value);
            return;
        }
    }
}
