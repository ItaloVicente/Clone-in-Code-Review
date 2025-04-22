
		final boolean highContrast = Display.getCurrent().getHighContrast();

		Display.getCurrent().addListener(SWT.Settings, new Listener() {
			public void handleEvent(Event event) {
				updateThemes();
			}
		});

		if (highContrast)
			themeId = SYSTEM_DEFAULT_THEME;
