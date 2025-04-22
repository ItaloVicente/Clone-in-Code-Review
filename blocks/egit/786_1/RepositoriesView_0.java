	private void createRepositoryChangedListener() {
		repositoryListener = new RepositoryListener() {
			public void refsChanged(RefsChangedEvent e) {
				scheduleRefresh();
			}

			public void indexChanged(IndexChangedEvent e) {
				scheduleRefresh();
			}
		};
	}

	private void unregisterRepositoryListener() {
		for (Repository repo:repositories)
			repo.removeRepositoryChangedListener(repositoryListener);
	}


