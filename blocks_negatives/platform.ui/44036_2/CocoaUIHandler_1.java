	/** Unconfigure the handler */
	@PreDestroy
	public void dispose() {
		if (shellListener != null) {
			eventBroker.unsubscribe(shellListener);
		}
		if (menuListener != null) {
			eventBroker.unsubscribe(menuListener);
		}
		if (menuContributionListener != null) {
			eventBroker.unsubscribe(menuContributionListener);
		}
		if (commandListener != null) {
			eventBroker.unsubscribe(commandListener);
		}
		if (tagListener != null) {
			eventBroker.unsubscribe(tagListener);
		}
	}

