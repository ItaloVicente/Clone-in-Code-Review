		eventBroker.subscribe(UIEvents.UILifeCycle.THEME_DEFINITION_CHANGED,
				themeDefinitionChanged);
	}

	@PreDestroy
	protected void contextDisposed() {
		eventBroker.unsubscribe(topWindowHandler);
		eventBroker.unsubscribe(shellUpdater);
		eventBroker.unsubscribe(visibilityHandler);
		eventBroker.unsubscribe(sizeHandler);
		eventBroker.unsubscribe(themeDefinitionChanged);

		themeDefinitionChanged.dispose();
