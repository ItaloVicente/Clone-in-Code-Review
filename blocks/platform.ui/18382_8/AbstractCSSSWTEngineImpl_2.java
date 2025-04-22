package org.eclipse.e4.ui.css.core.resources;

import org.w3c.dom.css.CSSValue;

public class ResourceRegistryKeyFactory {
	public Object createKey(CSSValue value) {
		return CSSResourcesHelpers.getCSSValueKey(value);
	}
}
