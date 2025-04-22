package org.eclipse.ui.examples.views.properties.tabbed.article.views;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class ButtonElementProperties
	implements IPropertySource {

	final protected ButtonElement element;

	protected static final String PROPERTY_FONT = "font"; //$NON-NLS-1$

	protected static final String PROPERTY_SIZE = "size"; //$NON-NLS-1$

	protected static final String PROPERTY_TEXT = "text"; //$NON-NLS-1$

	private final Object PropertiesTable[][] = {
		{PROPERTY_FONT, new FontPropertyDescriptor(PROPERTY_FONT, "Font")},//$NON-NLS-1$
		{PROPERTY_SIZE, new PropertyDescriptor(PROPERTY_SIZE, "Size")},//$NON-NLS-1$
		{PROPERTY_TEXT, new TextPropertyDescriptor(PROPERTY_TEXT, "Text")}, //$NON-NLS-1$
	};

	String strFont = "";//$NON-NLS-1$

	Point ptSize = null;

	String strText = "";//$NON-NLS-1$

	protected void firePropertyChanged(String propName, Object value) {
		Button ctl = element.getControl();

		if (ctl == null) {
			return;
		}

		if (propName.equals(PROPERTY_FONT)) {
			ctl
				.setFont(new Font(ctl.getDisplay(),
					new FontData((String) value)));
			return;
		}
		if (propName.equals(PROPERTY_TEXT)) {
			ctl.setText((String) value);
			return;
		}

	}

	protected void initProperties() {
		Button ctl = element.getControl();

		if (ctl == null) {
			return;
		}

		strText = ctl.getText();
		ptSize = ctl.getSize();
	}

	public ButtonElementProperties(ButtonElement element) {
		super();
		this.element = element;
		initProperties();
	}

	public Object getEditableValue() {
		return this;
	}

	public IPropertyDescriptor[] getPropertyDescriptors() {
		IPropertyDescriptor[] propertyDescriptors = new IPropertyDescriptor[PropertiesTable.length];

		for (int i = 0; i < PropertiesTable.length; i++) {

			PropertyDescriptor descriptor;

			descriptor = (PropertyDescriptor) PropertiesTable[i][1];
			propertyDescriptors[i] = descriptor;
			descriptor.setCategory("Basic");//$NON-NLS-1$
		}

		return propertyDescriptors;

	}

	public Object getPropertyValue(Object name) {
		if (name.equals(PROPERTY_FONT))
			return strFont;
		if (name.equals(PROPERTY_SIZE))
			return new SizePropertySource(element, ptSize);
		if (name.equals(PROPERTY_TEXT))
			return strText;

		return null;
	}

	public boolean isPropertySet(Object id) {
		return false;
	}

	public void resetPropertyValue(Object id) {
	}

	public void setPropertyValue(Object name, Object value) {
		firePropertyChanged((String) name, value);

		if (name.equals(PROPERTY_FONT)) {
			strFont = (String) value;
			return;
		}
		if (name.equals(PROPERTY_TEXT)) {
			strText = (String) value;
			return;
		}
		if (name.equals(PROPERTY_SIZE)) {
			SizePropertySource sizeProp = (SizePropertySource) value;
			ptSize = sizeProp.getValue();
		}

	}

	public ButtonElement getButtonElement() {
		return element;
	}

}
