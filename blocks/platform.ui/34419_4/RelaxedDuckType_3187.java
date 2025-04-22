
package org.eclipse.jface.examples.databinding.ducks;

public class ReflectedProperty {
	private String propertyName;
	private ReflectedMethod getter;
	private ReflectedMethod setter;

	public ReflectedProperty(Object object, String propertyName) {
		this.propertyName = propertyName;
		getter = new ReflectedMethod(object, makeGetterName(propertyName), new Class[] {});
		if (!getter.exists()) {
			getter = new ReflectedMethod(object, makeBooleanGetterName(propertyName), new Class[] {});
			if (!getter.exists()) {
				throw new IllegalArgumentException("Cannot find getter for " + propertyName);
			}
		}
		setter = new ReflectedMethod(object, makeSetterName(propertyName), new Class[] {getter.getType()});
	}

	private String makeBooleanGetterName(String propertyName) {
		return "is" + capitalize(propertyName);
	}

	private String makeSetterName(String propertyName) {
		return "set" + capitalize(propertyName);
	}

	private String makeGetterName(String propertyName) {
		return "get" + capitalize(propertyName);
	}

	private String capitalize(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	public Class getType() {
		return getter.getType();
	}

	public Object get() {
		return getter.invoke(new Object[] {});
	}

	public void set(Object newValue) {
		setter.invoke(new Object[] {newValue});
	}

	public boolean isReadOnly() {
		return !setter.exists();
	}

	public String getPropertyName() {
		return propertyName;
	}
}
