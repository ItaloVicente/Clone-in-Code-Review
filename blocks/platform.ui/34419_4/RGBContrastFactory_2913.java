
package org.eclipse.ui.internal.themes;

import java.util.Hashtable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.themes.ColorUtil;
import org.eclipse.ui.themes.IColorFactory;

public class RGBBrightnessColorFactory implements IColorFactory, IExecutableExtension {

	String color, scaleFactor;

	@Override
	public RGB createColor() {
		RGB rgb = ColorUtil.getColorValue(color);
		float scale = Float.parseFloat(scaleFactor);
		float[] hsb = rgb.getHSB();
		float b = hsb[2] * scale;
		if (b < 0)
			b = 0;
		if (b > 1)
			b = 1;
		return new RGB(hsb[0], hsb[1], b);
	}

	@Override
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		if (data instanceof Hashtable) {
			Hashtable table = (Hashtable) data;
			color = (String) table.get("color"); //$NON-NLS-1$
			scaleFactor = (String) table.get("scaleFactor"); //$NON-NLS-1$            
		}
	}

}
