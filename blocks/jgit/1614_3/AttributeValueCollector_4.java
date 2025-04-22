
package org.eclipse.jgit.attributes;

public class AttributeValueCollector implements AttributesCollector {

	private final String key;

	private AttributeValue value;

	public AttributeValueCollector(String key) {
		this.key = key;
	}

	public boolean collect(Attribute attribute) {
		if (key.equals(attribute.getKey())) {
			value = attribute.getValue();
			return false;
		}

		return true;
	}

	public AttributeValue getValue() {
		return value;
	}
}
