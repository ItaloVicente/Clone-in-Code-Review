		listenerCalled = new AtomicBoolean(false);
		indexDiffDataResult = new AtomicReference<>(null);
		indexDiffListener = new IndexDiffChangedListener() {
			@Override
			public void indexDiffChanged(Repository repo,
					IndexDiffData indexDiffData) {
				listenerCalled.set(true);
				indexDiffDataResult.set(indexDiffData);
			}
		};
