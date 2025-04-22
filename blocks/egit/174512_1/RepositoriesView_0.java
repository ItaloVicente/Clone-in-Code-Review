package org.eclipse.egit.ui.internal.repository;

import java.util.Collections;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class ListPropertySource implements IPropertySource {

	private final List<?> values;

	private final IPropertyDescriptor[] descriptors;

	public ListPropertySource(List<?> values) {
		this.values = values == null ? Collections.emptyList() : values;
		IPropertyDescriptor[] result = new IPropertyDescriptor[this.values
				.size()];
		for (int i = 1; i <= result.length; i++) {
			result[i - 1] = new PropertyDescriptor(Integer.valueOf(i),
					Integer.toString(i));
		}
		descriptors = result;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof Integer) {
			int index = ((Integer) id).intValue() - 1;
			if (index >= 0 && index < values.size()) {
				return values.get(index);
			}
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public Object getEditableValue() {
		return this;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

	@Override
	public String toString() {
		return values.isEmpty() ? "" : values.toString(); //$NON-NLS-1$
	}
}
