package org.eclipse.e4.ui.css.swt.properties.definition;

import org.eclipse.e4.ui.css.core.dom.properties.css2.AbstractCSSPropertyFontHandler;
import org.eclipse.e4.ui.css.core.dom.properties.css2.CSS2FontProperties;
import org.eclipse.e4.ui.css.core.dom.properties.css2.CSS2FontPropertiesImpl;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.definition.FontDefinitionElement;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTFontHelper;
import org.eclipse.e4.ui.internal.css.swt.definition.IFontDefinitionOverridable;
import org.eclipse.swt.graphics.FontData;
import org.w3c.dom.css.CSSValue;

public class CSSPropertyFontDefinitionHandler extends AbstractCSSPropertyFontHandler {
	public boolean applyCSSProperty(Object element, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (element instanceof FontDefinitionElement) {
			CSS2FontProperties properties = new CSS2FontPropertiesImpl();
			IFontDefinitionOverridable definition = (IFontDefinitionOverridable) ((FontDefinitionElement) element).getNativeWidget();
			
			super.applyCSSProperty(properties, property, value, pseudo, engine);
			setFontProperties(definition, properties);
		}		
		return false;
	}

	public String retrieveCSSProperty(Object element, String property,
			String pseudo, CSSEngine engine) throws Exception {
		return null;
	}
	
	private void setFontProperties(IFontDefinitionOverridable definition, CSS2FontProperties properties) {
		FontData fontData = definition.getValue() != null? definition.getValue()[0]: null;
		definition.setValue(new FontData[]{CSSSWTFontHelper.getFontData(properties, fontData)});
	}
	
	public String retrieveCSSPropertyFontFamily(Object element, String pseudo,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontSize(Object element, String pseudo,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontAdjust(Object element, String pseudo,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontStretch(Object element, String pseudo,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontStyle(Object element, String pseudo,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontVariant(Object element, String pseudo,
			CSSEngine engine) throws Exception {
		return null;
	}

	public String retrieveCSSPropertyFontWeight(Object element, String pseudo,
			CSSEngine engine) throws Exception {
		return null;
	}
}
