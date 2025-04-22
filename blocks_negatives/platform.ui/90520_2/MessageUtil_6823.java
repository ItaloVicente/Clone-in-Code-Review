/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.readmetool;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

/**
 * This class encapsulates property sheet properties
 * for MarkElement.  This will display properties for
 * the MarkElement when selected in the readme editor's
 * content outline.
 */
public class MarkElementProperties implements IPropertySource {
    protected MarkElement element;




    /**
     * Creates a new MarkElementProperties.
     *
     * @param element  the element whose properties this instance represents
     */
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
        propertyDescriptors[0] = descriptor;
        descriptor = new PropertyDescriptor(PROPERTY_START, MessageUtil
        propertyDescriptors[1] = descriptor;
        descriptor = new PropertyDescriptor(PROPERTY_LENGTH, MessageUtil
        propertyDescriptors[2] = descriptor;

        return propertyDescriptors;
    }

    @Override
	public Object getPropertyValue(Object name) {
        if (name.equals(PROPERTY_LINECOUNT))
            return Integer.valueOf(element.getNumberOfLines());
        if (name.equals(PROPERTY_START))
            return Integer.valueOf(element.getStart());
        if (name.equals(PROPERTY_LENGTH))
            return Integer.valueOf(element.getLength());
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
