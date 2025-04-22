package org.eclipse.ui.internal.themes;

import java.util.Hashtable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.util.Util;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.themes.ColorUtil;
import org.eclipse.ui.themes.IColorFactory;

public class RGBInfoColorFactory implements IColorFactory, IExecutableExtension {
	String color;

	@Override
	public RGB createColor() {
		RGB rgb = new RGB(0, 0, 0); // IColorFactory must return a valid color

		if (color == null) {
			return rgb;
		}
		if (Util.isGtk()) {
			switch (color) {
			case "foreground": //$NON-NLS-1$
				rgb = ColorUtil.getColorValue("COLOR_LIST_FOREGROUND"); //$NON-NLS-1$
				break;
			case "background": //$NON-NLS-1$
				rgb = ColorUtil.getColorValue("COLOR_LIST_BACKGROUND"); //$NON-NLS-1$
				break;
			}
		} else {
			switch (color) {
			case "foreground": //$NON-NLS-1$
				rgb = ColorUtil.getColorValue("COLOR_INFO_FOREGROUND"); //$NON-NLS-1$
				break;
			case "background": //$NON-NLS-1$
				rgb = ColorUtil.getColorValue("COLOR_INFO_BACKGROUND");  //$NON-NLS-1$
				break;
			}
		}
		return rgb;
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) {
		if (data instanceof Hashtable<?, ?>) {
			Hashtable<?, ?> map = (Hashtable<?, ?>) data;
			color = (String) map.get("color"); //$NON-NLS-1$
		}
	}
}
