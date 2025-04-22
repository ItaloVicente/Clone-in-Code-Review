		IndexDiffCache diffCache = IndexDiffCache.getInstance();
		if (diffCache != null) {
			IndexDiffCacheEntry entry = diffCache.getIndexDiffCacheEntry(repo);
			return entry != null
					&& entry.getIndexDiff().getConflicting().isEmpty();
		}
		return false;
