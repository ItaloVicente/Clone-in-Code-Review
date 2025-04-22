		IndexDiffCache indexDiffCache = Activator.getDefault()
				.getIndexDiffCache();
		IndexDiffCacheEntry cacheEntry = indexDiffCache
				.getIndexDiffCacheEntry(repository);
		final AtomicBoolean listenerCalled = new AtomicBoolean(false);
		final AtomicReference<IndexDiffData> resultDiff = new AtomicReference<IndexDiffData>(
				null);
		cacheEntry.addIndexDiffChangedListener(new IndexDiffChangedListener() {
			public void indexDiffChanged(Repository repo,
					IndexDiffData indexDiffData) {
				listenerCalled.set(true);
				resultDiff.set(indexDiffData);
			}
		});
		waitForListenerCalled(listenerCalled);
