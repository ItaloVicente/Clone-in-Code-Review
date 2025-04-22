	/**
	 * Unregisters the listeners
	 */
	private void unregisterModelListeners() {
		eventBroker.unsubscribe(eventHandler);
		eventBroker.unsubscribe(contextHandler);
