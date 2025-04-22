		tags.add(PROCESSOR_ID);

		IThemeEngine engine = mgr.getEngineForDisplay(Display.getCurrent());
		List<ITheme> themes = engine.getThemes();

		MCommand switchThemeCommand = findCommand(app);

		if (themes.size() <= 0 || switchThemeCommand == null) {
