		assertNotInitialized();
		receivePackFilters.add(filter);
	}

	private void assertNotInitialized() {
		if (initialized)
			throw new IllegalStateException(HttpServerText.get().alreadyInitializedByContainer);
