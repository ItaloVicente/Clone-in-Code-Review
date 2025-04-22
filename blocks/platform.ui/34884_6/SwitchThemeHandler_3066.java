package org.eclipse.e4.demo.contacts.handlers;

import javax.inject.Named;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.css.swt.theme.IThemeEngine;

public class SwitchThemeHandler {
	@Execute
	public void switchTheme(
			@Named("contacts.commands.switchtheme.themeid") String themeId,
			IThemeEngine engine) {
		engine.setTheme(themeId, true);
	}
}
