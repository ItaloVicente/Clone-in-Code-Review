
package org.eclipse.jgit.attributes;

import java.util.ArrayList;
import java.util.List;

public class AttributesAllCollector implements AttributesCollector {

	private final List<Attribute> attributes = new ArrayList<Attribute>();

	public boolean collect(Attribute attribute) {
		if (attribute.getValue() != null)
			attributes.add(attribute);

		return true;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
}
