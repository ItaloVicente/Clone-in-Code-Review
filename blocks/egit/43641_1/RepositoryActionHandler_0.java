	protected boolean selectionContainsTrackedFiles() {
		Map<Repository, Collection<String>> m = ResourceUtil
				.splitResourcesByRepository(getSelectedResources());
		for (Repository repo : m.keySet()) {
			IndexDiffData indexDiff = getIndexDiff(repo);
			for (String path : m.get(repo)) {
				if (!indexDiff.getUntracked().contains(path)) {
					return true;
				}
			}
		}
		return false;
	}

	protected IndexDiffData getIndexDiff(Repository repo) {
		IndexDiffCache indexDiffCache = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache();
		IndexDiffCacheEntry indexDiffCacheEntry = indexDiffCache
				.getIndexDiffCacheEntry(repo);
		IndexDiffData indexDiff = indexDiffCacheEntry.getIndexDiff();
		return indexDiff;
	}

