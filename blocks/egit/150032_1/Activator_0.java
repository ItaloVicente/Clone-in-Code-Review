	private void setupResourceRefresh() {
		refreshHandler = new ResourceRefreshHandler();
		refreshHandle = Repository.getGlobalListenerList()
				.addWorkingTreeModifiedListener(refreshHandler);
	}

