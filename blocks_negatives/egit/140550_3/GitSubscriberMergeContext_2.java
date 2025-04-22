		indexChangeListener = new IndexDiffChangedListener() {
			@Override
			public void indexDiffChanged(Repository repository,
					IndexDiffData indexDiffData) {
				handleRepositoryChange(repository);
			}
