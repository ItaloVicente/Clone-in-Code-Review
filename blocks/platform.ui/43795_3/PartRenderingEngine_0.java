	@Inject
	@Optional
	private void subscribePerspectiveWindowsHandler(@EventTopic(UIEvents.Perspective.TOPIC_WINDOWS) Event event) {
		subscribeChildrenHandler(event);
	}

	@Inject
	@Optional
	private void subscribeCssThemeChanged(@EventTopic(IThemeEngine.Events.THEME_CHANGED) Event event) {
		cssThemeChangedHandler.handleEvent(event);
	}

