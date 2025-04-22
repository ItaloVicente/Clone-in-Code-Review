package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.graphics.Image;

public class PropertyColumnLabelProvider extends ColumnLabelProvider {
	protected IPropertySourceProvider propertySourceProvider;
	protected Object propertyID;

	public PropertyColumnLabelProvider(
			IPropertySourceProvider propertySourceProvider, Object propertyID) {
		this.propertySourceProvider = propertySourceProvider;
		this.propertyID = propertyID;
	}

	@Override
	public String getText(Object object) {
		IPropertySource propertySource = propertySourceProvider
				.getPropertySource(object);
		IPropertyDescriptor[] propertyDescriptors = propertySource
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			IPropertyDescriptor propertyDescriptor = propertyDescriptors[i];
			if (propertyID.equals(propertyDescriptor.getId())) {
				return propertyDescriptor.getLabelProvider().getText(
						propertySource.getPropertyValue(propertyID));
			}
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	public Image getImage(Object object) {
		IPropertySource propertySource = propertySourceProvider
				.getPropertySource(object);
		IPropertyDescriptor[] propertyDescriptors = propertySource
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			IPropertyDescriptor propertyDescriptor = propertyDescriptors[i];
			if (propertyID.equals(propertyDescriptor.getId())) {
				return propertyDescriptor.getLabelProvider().getImage(
						propertySource.getPropertyValue(propertyID));
			}
		}
		return null;
	}
}
