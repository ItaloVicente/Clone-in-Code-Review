
	public static boolean hasChanges(@NonNull Repository repository) {
		IndexDiffCacheEntry entry = Activator.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(repository);
		IndexDiffData data = entry != null ? entry.getIndexDiff() : null;
		return data != null && data.hasChanges();
	}
