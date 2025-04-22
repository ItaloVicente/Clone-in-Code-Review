			IndexDiffCacheEntry diffCacheEntry = diffCache
					.getIndexDiffCacheEntry(
					repository);
			if (diffCacheEntry == null) {
				return true;
			}
			IndexDiffData diffData = diffCacheEntry.getIndexDiff();
