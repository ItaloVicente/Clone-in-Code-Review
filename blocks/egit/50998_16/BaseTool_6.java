
package org.eclipse.egit.ui.internal.externaltools;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AttributeSet {

	private Set<Attribute> attributes = new HashSet<>();

	public void addAttribute(String name, String value) {
		removeAttribute(name);
		Attribute attr = new Attribute(name, value);
		attributes.add(attr);
	}

	public void removeAttribute(String name) {
		Attribute attr = getAttribute(name);
		if (attr != null) {
			attributes.remove(attr);
		}
	}

	public Attribute getAttribute(String name) {
		for (Iterator<Attribute> iterator = attributes
				.iterator(); iterator.hasNext();) {
			Attribute attr = iterator.next();
			if (attr.getName().equals(name)) {
				return attr;
			}
		}
		return null;
	}

}
