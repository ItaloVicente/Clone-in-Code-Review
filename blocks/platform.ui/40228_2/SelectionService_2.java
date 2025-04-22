	@PreDestroy
	public void dispose() {
		setSelectionService(null);
		selectionService = null;
		listeners.clear();
		postSelectionListeners.clear();
		targetedListeners.clear();
		targetedPostSelectionListeners.clear();
	}

