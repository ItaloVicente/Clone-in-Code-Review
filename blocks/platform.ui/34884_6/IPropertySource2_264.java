package org.eclipse.ui.views.properties;

public interface IPropertySource {

	public Object getEditableValue();

	public IPropertyDescriptor[] getPropertyDescriptors();

	public Object getPropertyValue(Object id);

	public boolean isPropertySet(Object id);

	public void resetPropertyValue(Object id);

	public void setPropertyValue(Object id, Object value);
}
