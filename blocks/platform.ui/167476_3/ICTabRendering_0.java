
package org.eclipse.e4.ui.css.swt.properties.custom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.e4.ui.internal.css.swt.ICTabRendering;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.widgets.Control;
import org.w3c.dom.css.CSSValue;

public class CSSPropertyCustomTabContentBackgroundSWTHandler extends
AbstractCSSPropertySWTHandler {

	@Override
	protected void applyCSSProperty(Control control, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
		if (!(control instanceof CTabFolder) || value.getCssValueType() != CSSValue.CSS_PRIMITIVE_VALUE) {
			return;
		}

		CTabFolder tabFolder = (CTabFolder) control;
		Boolean custom = (Boolean) engine.convert(value, Boolean.class, control.getDisplay());
		if (tabFolder.getRenderer() instanceof ICTabRendering) {
			((ICTabRendering) tabFolder.getRenderer()).setDrawCustomTabContentBackground(custom);
		}
	}

	@Override
	protected String retrieveCSSProperty(Control control, String property, String pseudo, CSSEngine engine)
			throws Exception {
		return null;
	}

}
