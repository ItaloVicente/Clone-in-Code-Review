
		if (eventBroker != null) {
			eventBroker.subscribe(UIEvents.UIElement.TOPIC_TOBERENDERED,
					toBeRenderedHandler);
			eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE,
					visibilityHandler);
			eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN,
					childrenHandler);
			eventBroker
					.subscribe(UIEvents.Window.TOPIC_WINDOWS, windowsHandler);
			eventBroker.subscribe(UIEvents.Perspective.TOPIC_WINDOWS,
					windowsHandler);
			eventBroker.subscribe(UIEvents.TrimmedWindow.TOPIC_TRIMBARS,
					trimHandler);

			cssThemeChangedHandler = new StylingPreferencesHandler(
					context.get(Display.class));
			eventBroker.subscribe(IThemeEngine.Events.THEME_CHANGED,
					cssThemeChangedHandler);
		}
	}

	@PreDestroy
	void contextDisposed() {
		if (eventBroker == null)
			return;
		eventBroker.unsubscribe(toBeRenderedHandler);
		eventBroker.unsubscribe(visibilityHandler);
		eventBroker.unsubscribe(childrenHandler);
		eventBroker.unsubscribe(trimHandler);
		eventBroker.unsubscribe(cssThemeChangedHandler);
