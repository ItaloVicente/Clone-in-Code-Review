	private boolean haveSubmoduleChanges(@NonNull Repository repository) {
		IndexDiffCache cache = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache();
		if (cache == null) {
			return false;
		}
		IndexDiffCacheEntry entry = cache.getIndexDiffCacheEntry(repository);
		IndexDiffData data = entry != null ? entry.getIndexDiff() : null;
		if (data == null) {
			return false;
