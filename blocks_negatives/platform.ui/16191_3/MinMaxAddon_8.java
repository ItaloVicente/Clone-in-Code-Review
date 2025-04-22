	@PreDestroy
	void unhookListeners() {
		eventBroker.unsubscribe(widgetListener);
		eventBroker.unsubscribe(perspectiveRemovedListener);
		eventBroker.unsubscribe(perspectiveChangeListener);
		eventBroker.unsubscribe(tagChangeListener);
		eventBroker.unsubscribe(idChangeListener);
		eventBroker.unsubscribe(perspSavedListener);
		eventBroker.unsubscribe(perspOpenedListener);
