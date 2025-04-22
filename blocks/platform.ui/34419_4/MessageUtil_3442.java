package org.eclipse.ui.examples.readmetool;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class MarkElementProperties implements IPropertySource {
    protected MarkElement element;

    protected static final String PROPERTY_LINECOUNT = "lineno"; //$NON-NLS-1$

    protected static final String PROPERTY_START = "start"; //$NON-NLS-1$

    protected static final String PROPERTY_LENGTH = "length"; //$NON-NLS-1$

    public MarkElementProperties(MarkElement element) {
        super();
        this.element = element;
    }

    @Override
	public Object getEditableValue() {
        return this;
    }

    @Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
        IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[3];

        PropertyDescriptor descriptor;

        descriptor = new PropertyDescriptor(PROPERTY_LINECOUNT, MessageUtil
                .getString("Line_count")); //$NON-NLS-1$
        propertyDescriptors[0] = descriptor;
        descriptor = new PropertyDescriptor(PROPERTY_START, MessageUtil
                .getString("Title_start")); //$NON-NLS-1$
        propertyDescriptors[1] = descriptor;
        descriptor = new PropertyDescriptor(PROPERTY_LENGTH, MessageUtil
                .getString("Title_length")); //$NON-NLS-1$
        propertyDescriptors[2] = descriptor;

        return propertyDescriptors;
    }

    @Override
	public Object getPropertyValue(Object name) {
        if (name.equals(PROPERTY_LINECOUNT))
            return new Integer(element.getNumberOfLines());
        if (name.equals(PROPERTY_START))
            return new Integer(element.getStart());
        if (name.equals(PROPERTY_LENGTH))
            return new Integer(element.getLength());
        return null;
    }

    @Override
	public boolean isPropertySet(Object property) {
        return false;
    }

    @Override
	public void resetPropertyValue(Object property) {
    }

    @Override
	public void setPropertyValue(Object name, Object value) {
    }
}
