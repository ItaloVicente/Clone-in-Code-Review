	@Inject
	@Optional
	private void subscribeCssThemeChanged(@EventTopic(IThemeEngine.Events.THEME_CHANGED) Event event) {
		cssThemeChangedHandler.handleEvent(event);
	}

