package org.eclipse.e4.ui.css.swt.properties.custom;

import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.e4.ui.internal.css.swt.IHeapStatus;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.w3c.dom.css.CSSValue;

public class CSSPropertyHeapStatusColorsHandler extends AbstractCSSPropertySWTHandler {

	private static final String USED_MEM_COLOR = "swt-used-mem-color";
	private static final String LOW_MEM_COLOR = "swt-low-mem-color";
	private static final String FREE_MEM_COLOR = "swt-free-mem-color";

	@Override
	protected void applyCSSProperty(Control control, String property, CSSValue value, String pseudo, CSSEngine engine)
			throws Exception {
		if (!(control instanceof IHeapStatus) || value.getCssValueType() != CSSValue.CSS_PRIMITIVE_VALUE) {
			return;
		}

		Color newColor = (Color) engine.convert(value, Color.class, control.getDisplay());
		IHeapStatus heapStatus = (IHeapStatus) control;
		switch (property) {
		case USED_MEM_COLOR:
			heapStatus.setUsedMemColor(newColor);
			break;
		case LOW_MEM_COLOR:
			heapStatus.setLowMemColor(newColor);
			break;
		case FREE_MEM_COLOR:
			heapStatus.setFreeMemColor(newColor);
			break;
		default:
			break;
		}
	}

	@Override
	protected String retrieveCSSProperty(Control control, String property, String pseudo, CSSEngine engine)
			throws Exception {
		if (control instanceof IHeapStatus) {
			IHeapStatus heapStatus = (IHeapStatus) control;
			RGB rgb;
			switch (property) {
			case USED_MEM_COLOR:
				rgb = heapStatus.getUsedMemColor().getRGB();
				break;
			case LOW_MEM_COLOR:
				rgb = heapStatus.getLowMemColor().getRGB();
				break;
			case FREE_MEM_COLOR:
				rgb = heapStatus.getFreeMemColor().getRGB();
				break;
			default:
				return null;
			}
			return CSS2ColorHelper.getHexaColorStringValue(rgb.red, rgb.green, rgb.blue);
		}
		return null;
	}

}
