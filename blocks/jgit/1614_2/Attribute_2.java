
package org.eclipse.jgit.attributes;

import org.eclipse.jgit.util.CompareUtils;

public class Attribute {

	private final String key;

	private final AttributeValue value;

	public Attribute(String key
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Attribute) {
			final Attribute attribute = (Attribute) obj;
			return CompareUtils.areEqual(key
					&& CompareUtils.areEqual(value
		}
		return false;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public String toString() {
	}

	public String getKey() {
		return key;
	}

	public AttributeValue getValue() {
		return value;
	}
}
