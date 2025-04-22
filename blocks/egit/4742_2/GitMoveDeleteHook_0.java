		String repoRelativePath = map.getRepoRelativePath(file);
		IndexDiffCache indexDiffCache = Activator.getDefault()
				.getIndexDiffCache();
		IndexDiffCacheEntry indexDiffCacheEntry = indexDiffCache
				.getIndexDiffCacheEntry(map.getRepository());
		IndexDiffData indexDiff = indexDiffCacheEntry.getIndexDiff();
		if (indexDiff.getUntracked().contains(repoRelativePath))
			return false;
		if (indexDiff.getIgnoredNotInIndex().contains(repoRelativePath))
			return false;
		if (!file.exists())
			return false;
		if (file.isDerived())
			return false;

