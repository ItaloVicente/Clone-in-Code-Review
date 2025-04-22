	@Nullable
	public GitItemState get(@Nullable Repository repository,
			@Nullable String gitPath) {
		if (repository == null || gitPath == null) {
			return null;
		}
		IndexDiffCacheEntry cache = IndexDiffCache.getInstance()
				.getIndexDiffCacheEntry(repository);
		if (cache == null) {
			return UNKNOWN_STATE;
		}
		IndexDiffData indexDiffData = cache.getIndexDiff();
		if (indexDiffData == null) {
			return UNKNOWN_STATE;
		}
		return extractFileProperties(indexDiffData, gitPath);
	}

