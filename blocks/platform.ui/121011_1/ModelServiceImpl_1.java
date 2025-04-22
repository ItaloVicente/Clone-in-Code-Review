	@PreDestroy
	void dispose() {
		if (handlerRegistration != null) {
			handlerRegistration.unregister();
		}
	}

