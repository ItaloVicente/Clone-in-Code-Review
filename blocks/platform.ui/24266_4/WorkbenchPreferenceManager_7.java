
		eventBroker = (IEventBroker) PlatformUI.getWorkbench().getService(IEventBroker.class);
		if (eventBroker != null) {
			cssThemeChangedHandler = new CSSThemeChangedHandler(this);
			eventBroker.subscribe(IThemeEngine.Events.THEME_CHANGED, cssThemeChangedHandler);
		}
	}

	public void dispose() {
		if (eventBroker != null) {
			eventBroker.unsubscribe(cssThemeChangedHandler);
			cssThemeChangedHandler.dispose();
		}
