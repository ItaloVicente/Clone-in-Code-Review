package org.eclipse.e4.ui.css.swt.dom.definition;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;

import org.eclipse.e4.ui.internal.css.swt.definition.IFontDefinitionOverridable;

public class FontDefinitionElement extends ThemeDefinitionElement<IFontDefinitionOverridable> {
	public FontDefinitionElement(IFontDefinitionOverridable definition,
			CSSEngine engine) {
		super(definition, engine);
	}
}
