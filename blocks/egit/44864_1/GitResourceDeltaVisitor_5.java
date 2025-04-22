	private boolean isIgnoredInOldIndex(String path) {
		if (gitIgnoreChanged) {
			return false;
		}
		IndexDiffCacheEntry entry = null;
		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		if (cache != null) {
			entry = cache.getIndexDiffCacheEntry(repository);
		}
