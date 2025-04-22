		Map<Repository, Collection<String>> pathsByRepo = ResourceUtil
				.splitResourcesByRepository(relevantResources);
		for (Map.Entry<Repository, Collection<String>> entry : pathsByRepo
				.entrySet()) {
			Repository repository = entry.getKey();
			Collection<String> paths = entry.getValue();
			IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
			if (cache == null)
				continue;

			IndexDiffCacheEntry cacheEntry = cache.getIndexDiffCacheEntry(repository);
			if (cacheEntry == null)
				continue;

			IndexDiffData indexDiff = cacheEntry.getIndexDiff();
			if (indexDiff == null)
				continue;

			if (hasAnyPathChanged(paths, indexDiff))
				return super.promptForInputChange(requestPreviewMessage,
						monitor);
		}
