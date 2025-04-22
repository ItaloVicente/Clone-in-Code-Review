	protected static Set<String> getConflictingFiles(Repository repo) {
		IndexDiffCache cache = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache();
		if (cache == null)
			return Collections.emptySet();

		IndexDiffCacheEntry entry = cache.getIndexDiffCacheEntry(repo);
		if (entry == null || entry.getIndexDiff() == null)
			return Collections.emptySet();

		return entry.getIndexDiff().getConflicting();
	}

