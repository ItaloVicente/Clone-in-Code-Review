		if (!needRefresh.isEmpty()) {
			refreshIndex(event.getRepository(), needRefresh);
		}
	}

	private void refreshIndex(Repository repository,
			Collection<String> toRefresh) {
		IndexDiffCacheEntry cache = IndexDiffCache.getInstance()
				.getIndexDiffCacheEntry(repository);
		if (cache != null) {
			cache.refreshFiles(toRefresh);
		}
