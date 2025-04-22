package org.eclipse.e4.ui.css.swt.resources;

import org.eclipse.e4.ui.css.core.resources.ResourceRegistryKeyFactory;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTFontHelper;
import org.w3c.dom.css.CSSValue;

public class SWTResourceRegistryKeyFactory extends ResourceRegistryKeyFactory {
	@Override
	public Object createKey(CSSValue value) {
		Object key = super.createKey(value);
		if (CSSSWTColorHelper.hasColorDefinitionAsValue(value)
				|| CSSSWTFontHelper.hasFontDefinitionAsFamily(value)) {
			return new ResourceByDefinitionKey(key);
		}
		return key;
	}
}
