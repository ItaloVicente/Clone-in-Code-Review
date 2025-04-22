package org.eclipse.ui.internal.themes;
import org.eclipse.jface.util.Util;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.themes.ColorUtil;
import org.eclipse.ui.themes.IColorFactory;

public class DocumentationHoverColorFactory implements IColorFactory {

	@Override
	public RGB createColor() {
		RGB rgb = null;
		if (Util.isGtk()) {
			rgb = ColorUtil.getColorValue("COLOR_INFO_FOREGROUND"); //$NON-NLS-1$
		} else
				rgb = ColorUtil.getColorValue("COLOR_LIST_BACKGROUND"); //$NON-NLS-1$

		if (rgb == null) {
			return new RGB(0, 0, 0); // Black
		}
		return rgb;
	}


}
