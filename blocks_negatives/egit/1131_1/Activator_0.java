		setupRepoIndexRefresh();
	}

	private void setupRepoIndexRefresh() {
		refreshJob = new RIRefresh();
		Repository.addAnyRepositoryChangedListener(refreshJob);
