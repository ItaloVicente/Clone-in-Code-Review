				scheduleRefresh(DEFAULT_REFRESH_DELAY);
			}
		};

		repositoryListener = new RepositoryListener() {
			public void refsChanged(RefsChangedEvent e) {
				lastRepositoryChange = System.currentTimeMillis();
				scheduleRefresh(DEFAULT_REFRESH_DELAY);
			}

			public void indexChanged(IndexChangedEvent e) {
				lastRepositoryChange = System.currentTimeMillis();
				scheduleRefresh(DEFAULT_REFRESH_DELAY);
