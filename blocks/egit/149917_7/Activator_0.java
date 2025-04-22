	private void setupRepoResourceRefresh() {
		refreshJob = new ResourceRefreshJob();
		refreshHandle = Repository.getGlobalListenerList()
				.addWorkingTreeModifiedListener(refreshJob);
	}

