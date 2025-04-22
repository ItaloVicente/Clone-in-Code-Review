
package org.eclipse.ui.internal.themes;

import org.eclipse.e4.ui.internal.css.swt.definition.IColorAndFontProvider;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.themes.ITheme;

public class ColorAndFontProviderImpl implements IColorAndFontProvider {

	@Override
	public FontData[] getFont(String symbolicName) {
		return getCurrentTheme().getFontRegistry().getFontData(symbolicName);
	}

	@Override
	public RGB getColor(String symbolicName) {
		return getCurrentTheme().getColorRegistry().getRGB(symbolicName);
	}

	private ITheme getCurrentTheme() {
		return Workbench.getInstance().getThemeManager().getCurrentTheme();
	}
}
