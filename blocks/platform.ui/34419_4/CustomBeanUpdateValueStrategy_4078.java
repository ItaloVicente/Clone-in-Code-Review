
package org.eclipse.jface.tests.databinding.scenarios;

public class CustomBeanModelType {

	private String propertyName;

	private Object object;

	private Class type;

	public CustomBeanModelType(Object object, String propertyName, Class type) {
		this.object = object;
		this.propertyName = propertyName;
		this.type = type;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public Object getObject() {
		return object;
	}

	public Class getType() {
		return type;
	}

}
