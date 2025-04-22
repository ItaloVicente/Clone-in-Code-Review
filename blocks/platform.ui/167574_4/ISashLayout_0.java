package org.eclipse.e4.ui.css.swt.properties.custom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.e4.ui.internal.css.swt.ISashLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.w3c.dom.css.CSSPrimitiveValue;
import org.w3c.dom.css.CSSValue;

public class CSSPropertySashLayoutWidthHandler extends AbstractCSSPropertySWTHandler {

	@Override
	public void applyCSSProperty(Control control, String property, CSSValue value, String pseudo, CSSEngine engine)
			throws Exception {
		if (control instanceof Composite && value.getCssValueType() == CSSValue.CSS_PRIMITIVE_VALUE) {
			Layout layout = ((Composite) control).getLayout();
			CSSPrimitiveValue primitiveValue = (CSSPrimitiveValue) value;
			if (layout instanceof ISashLayout && primitiveValue.getPrimitiveType() == CSSPrimitiveValue.CSS_PX) {
				int sashWidth = (int) primitiveValue.getFloatValue(CSSPrimitiveValue.CSS_PX);
				((ISashLayout) layout).setSashWidth(sashWidth);
			}
		}
	}

	@Override
	public String retrieveCSSProperty(Control control, String property, String pseudo, CSSEngine engine)
			throws Exception {
		if (control instanceof Composite) {
			Layout layout = ((Composite) control).getLayout();
			if (layout instanceof ISashLayout) {
				return Integer.toString(((ISashLayout) layout).getSashWidth());
			}
		}
		return null;
	}

}
