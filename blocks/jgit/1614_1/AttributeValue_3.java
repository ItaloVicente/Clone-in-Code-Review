
package org.eclipse.jgit.attributes;

import org.eclipse.jgit.util.CompareUtils;

public final class AttributeValue {

	public static final AttributeValue SET = new AttributeValue(null

	public static final AttributeValue UNSET = new AttributeValue(null

	public static AttributeValue createValue(String value) {
		if (value == null)
			throw new IllegalArgumentException();

		return new AttributeValue(value
	}

	private final String value;

	private final String id;

	private AttributeValue(String value
		this.value = value;
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass())
			return false;

		final AttributeValue attributeValue = (AttributeValue) obj;
		return CompareUtils.areEqual(id
				&& CompareUtils.areEqual(value
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return id;
	}

	public String getValue() {
		return value;
	}
}
