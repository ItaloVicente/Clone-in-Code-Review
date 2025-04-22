
	private void publishThemeChangedEvent(boolean partialThemeChanges) {
		if (eventBroker != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(UIEvents.EventTags.ELEMENT, workbench.getApplication());
			params.put(UIEvents.EventTypes.ADD_MANY, partialThemeChanges);
			eventBroker.send(UIEvents.UILifeCycle.THEME_DEFINITION_CHANGED, params);
		}
	}
