	@PreDestroy
	public void contextDisposed() {
		eventBroker.unsubscribe(itemUpdater);
		eventBroker.unsubscribe(selectionUpdater);
		eventBroker.unsubscribe(enabledUpdater);
		eventBroker.unsubscribe(toBeRenderedUpdater);
		eventBroker.unsubscribe(childAdditionUpdater);
	}

