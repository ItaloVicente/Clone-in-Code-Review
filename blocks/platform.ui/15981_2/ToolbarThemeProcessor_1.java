		IThemeEngine engine = mgr.getEngineForDisplay(Display.getCurrent());
		List<ITheme> themes = engine.getThemes();
		if (themes.size() > 0) {

			MCommand switchThemeCommand = null;
			for (MCommand cmd : app.getCommands()) {
				if ("contacts.switchTheme".equals(cmd.getElementId())) { //$NON-NLS-1$
					switchThemeCommand = cmd;
					break;
				}
			}
