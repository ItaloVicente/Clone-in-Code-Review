		globalListener = new IndexDiffChangedListener() {
			@Override
			public void indexDiffChanged(Repository repository,
					IndexDiffData indexDiffData) {
				notifyListeners(repository, indexDiffData);
			}
		};
