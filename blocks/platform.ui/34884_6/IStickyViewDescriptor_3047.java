package org.eclipse.ui.themes;

import java.util.Hashtable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.swt.graphics.RGB;

public class RGBBlendColorFactory implements IColorFactory,
        IExecutableExtension {

    private String color1, color2;

    @Override
	public RGB createColor() {
        if (color1 == null && color2 == null) {
            return new RGB(0, 0, 0);
        } else if (color1 != null && color2 == null) {
            return ColorUtil.getColorValue(color1);
        } else if (color1 == null && color2 != null) {
            return ColorUtil.getColorValue(color2);
        } else {
            RGB rgb1 = ColorUtil.getColorValue(color1);
            RGB rgb2 = ColorUtil.getColorValue(color2);
            return ColorUtil.blend(rgb1, rgb2);
        }
    }

    @Override
	public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {

        if (data instanceof Hashtable) {
            Hashtable table = (Hashtable) data;
            color1 = (String) table.get("color1"); //$NON-NLS-1$
            color2 = (String) table.get("color2"); //$NON-NLS-1$            
        }
    }
}
