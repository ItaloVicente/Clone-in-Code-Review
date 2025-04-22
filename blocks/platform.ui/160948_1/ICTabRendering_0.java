package org.eclipse.e4.ui.css.swt.properties.custom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.e4.ui.internal.css.swt.ICTabRendering;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.widgets.Control;
import org.w3c.dom.css.CSSValue;

public class CSSPropertyCornerShapeSWTHandler extends AbstractCSSPropertySWTHandler {

	public static final String TAB_CORNER_SHAPE = "swt-tab-corner-shape";

	@Override
	protected void applyCSSProperty(Control control, String property, CSSValue value, String pseudo, CSSEngine engine)
			throws Exception {
		if (!(control instanceof CTabFolder) || value.getCssValueType() != CSSValue.CSS_PRIMITIVE_VALUE) {
			return;
		}

		if (TAB_CORNER_SHAPE.equals(property)) {
			String cornerShape = value.getCssText().toLowerCase();
			if (cornerShape == null) {
				return;
			}

			CTabFolderRenderer renderer = ((CTabFolder) control).getRenderer();
			if (renderer instanceof ICTabRendering) {
				((ICTabRendering) renderer).setCornerShape(cornerShape.toUpperCase());
			}
		}
	}

	@Override
	protected String retrieveCSSProperty(Control control, String property, String pseudo, CSSEngine engine)
			throws Exception {
		return null;
	}

}
