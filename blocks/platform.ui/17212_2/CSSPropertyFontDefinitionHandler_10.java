package org.eclipse.e4.ui.css.swt.properties.definition;

import org.eclipse.e4.ui.css.core.dom.properties.ICSSPropertyHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.definition.ColorDefinitionElement;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper;
import org.eclipse.e4.ui.internal.css.swt.definition.IColorDefinitionOverridable;
import org.w3c.dom.css.CSSValue;

public class CSSPropertyColorDefinitionHandler implements ICSSPropertyHandler {
	private final static String COLOR_PROP = "color";
	
	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (element instanceof ColorDefinitionElement && COLOR_PROP.equals(property)) {
			IColorDefinitionOverridable definition = 
				(IColorDefinitionOverridable) ((ColorDefinitionElement) element).getNativeWidget();
			definition.setValue(CSSSWTColorHelper.getRGB(value));
		}		
		return false;
	}

	public String retrieveCSSProperty(Object element, String property,
			String pseudo, CSSEngine engine) throws Exception {
		return null;
	}	
}
