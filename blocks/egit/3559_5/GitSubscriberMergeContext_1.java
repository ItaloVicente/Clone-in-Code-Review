
		repoChangeListener = new RepositoryChangeListener() {
			public void repositoryChanged(RepositoryMapping which) {
				update(subscriber, which);
			}
		};
		GitProjectData.addRepositoryChangeListener(repoChangeListener);

