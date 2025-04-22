
	private void publishThemeChangedEvent() {
		if (eventBroker != null) {
			eventBroker.send(UIEvents.UILifeCycle.THEME_DEFINITION_CHANGED,
					workbench.getApplication());
		}
	}
