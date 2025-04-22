
package org.eclipse.egit.ui.internal.externaltools;

public class Attribute {
	private String name;
	private String value;

	public Attribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public boolean getValueBoolean() {
		return Boolean.parseBoolean(getValue());
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
