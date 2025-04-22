package org.eclipse.egit.ui;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.PlatformUI;

public class UIUtils {

	public static Font getFont(final String id) {
		return PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
				.getFontRegistry().get(id);
	}

	public static Font getBoldFont(final String id) {
		return PlatformUI.getWorkbench().getThemeManager().getCurrentTheme()
				.getFontRegistry().getBold(id);
	}

}
