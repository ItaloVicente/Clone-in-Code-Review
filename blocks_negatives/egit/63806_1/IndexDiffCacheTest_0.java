		listenerCalled = new AtomicBoolean(false);
		indexDiffDataResult = new AtomicReference<IndexDiffData>(
				null);
		cacheEntry.addIndexDiffChangedListener(new IndexDiffChangedListener() {
			@Override
			public void indexDiffChanged(Repository repo,
					IndexDiffData indexDiffData) {
				listenerCalled.set(true);
				indexDiffDataResult.set(indexDiffData);
			}
		});
