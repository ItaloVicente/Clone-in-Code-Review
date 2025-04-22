package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

public class PropertyEditingSupport extends EditingSupport {
	protected IPropertySourceProvider propertySourceProvider;
	protected Object propertyID;

	public PropertyEditingSupport(ColumnViewer viewer,
			IPropertySourceProvider propertySourceProvider, Object propertyID) {
		super(viewer);
		this.propertySourceProvider = propertySourceProvider;
		this.propertyID = propertyID;
	}

	@Override
	protected boolean canEdit(Object object) {
		IPropertySource propertySource = propertySourceProvider
				.getPropertySource(object);
		IPropertyDescriptor[] propertyDescriptors = propertySource
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			IPropertyDescriptor propertyDescriptor = propertyDescriptors[i];
			if (propertyID.equals(propertyDescriptor.getId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected CellEditor getCellEditor(Object object) {
		IPropertySource propertySource = propertySourceProvider
				.getPropertySource(object);
		IPropertyDescriptor[] propertyDescriptors = propertySource
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			IPropertyDescriptor propertyDescriptor = propertyDescriptors[i];
			if (propertyID.equals(propertyDescriptor.getId())) {
				return propertyDescriptor
						.createPropertyEditor((Composite) getViewer()
								.getControl());
			}
		}
		return null;
	}

	@Override
	protected Object getValue(Object object) {
		IPropertySource propertySource = propertySourceProvider
				.getPropertySource(object);
		Object value = propertySource.getPropertyValue(propertyID);
		IPropertySource valuePropertySource = propertySourceProvider
				.getPropertySource(value);
		if (valuePropertySource != null) {
			value = valuePropertySource.getEditableValue();
		}
		return value;
	}

	@Override
	protected void setValue(Object object, Object value) {
		IPropertySource propertySource = propertySourceProvider
				.getPropertySource(object);
		propertySource.setPropertyValue(propertyID, value);
	}
}
