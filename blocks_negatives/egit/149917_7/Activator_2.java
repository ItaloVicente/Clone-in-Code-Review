	private void setupRepoIndexRefresh() {
		refreshJob = new ResourceRefreshJob();
		refreshHandle = Repository.getGlobalListenerList()
				.addWorkingTreeModifiedListener(refreshJob);
	}

