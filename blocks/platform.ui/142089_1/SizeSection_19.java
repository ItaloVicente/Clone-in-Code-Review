package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class SizePropertySource
    implements IPropertySource {

    protected ButtonElement element;

    public static String ID_WIDTH = "Width"; //$NON-NLS-1$

    public static String ID_HEIGHT = "Height"; //$NON-NLS-1$

    protected static IPropertyDescriptor[] descriptors;

    static {
        descriptors = new IPropertyDescriptor[] {
            new TextPropertyDescriptor(ID_WIDTH, "width"),//$NON-NLS-1$
            new TextPropertyDescriptor(ID_HEIGHT, "height")//$NON-NLS-1$
        };
    }

    protected Point point = null;

    public SizePropertySource(ButtonElement m_element, Point point) {
        this.point = new Point(point.x, point.y);
        element = m_element;
    }

    protected void firePropertyChanged() {
        Control ctl = element.getControl();

        if (ctl == null) {
            return;
        }
        ctl.setSize(point);
    }

    public Object getEditableValue() {
        return this;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return descriptors;
    }

    public Object getPropertyValue(Object propName) {
        if (ID_WIDTH.equals(propName)) {
            return new String(Integer.valueOf(point.x).toString());
        }
        if (ID_HEIGHT.equals(propName)) {
            return new String(Integer.valueOf(point.y).toString());
        }
        return null;
    }

    public Point getValue() {
        return new Point(point.x, point.y);
    }

    public boolean isPropertySet(Object propName) {
        if (ID_WIDTH.equals(propName) || ID_HEIGHT.equals(propName))
            return true;
        return false;
    }

    public void resetPropertyValue(Object propName) {
    }

    public void setPropertyValue(Object propName, Object value) {
        int newInt;
        try {
            newInt = Integer.parseInt((String) value);
        } catch (NumberFormatException e) {
            newInt = -1;
        }

        if (newInt > 0) {
            if (ID_WIDTH.equals(propName)) {
                point.x = newInt;
            } else if (ID_HEIGHT.equals(propName)) {
                point.y = newInt;
            }
        }
        firePropertyChanged();
    }

    public String toString() {
        return point.toString();
    }

}
