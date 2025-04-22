package org.eclipse.e4.ui.css.swt.dom.definition;

import org.eclipse.e4.ui.internal.css.swt.definition.IColorDefinitionOverridable;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;

public class ColorDefinitionElement extends ThemeDefinitionElement<IColorDefinitionOverridable> {
	public ColorDefinitionElement(IColorDefinitionOverridable definition,
			CSSEngine engine) {
		super(definition, engine);
	}
}
