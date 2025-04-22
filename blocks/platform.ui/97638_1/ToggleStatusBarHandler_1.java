	@Override
	public void dispose() {
		if (eventBroker != null && eventHandler != null) {
			eventBroker.unsubscribe(eventHandler);
		}
		super.dispose();
	}

