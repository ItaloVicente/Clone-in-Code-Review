		IndexDiffCacheEntry diffCacheEntry = IndexDiffCache.INSTANCE
				.getIndexDiffCacheEntry(repository);
		if (diffCacheEntry == null) {
			return true;
		}
		IndexDiffData diffData = diffCacheEntry.getIndexDiff();
		if (diffData != null) {
			Set<String> modified = diffData.getModified();
			Set<String> untracked = diffData.getUntracked();
			Set<String> missing = diffData.getMissing();
			for (IResource resource : resources) {
				String repoRelativePath = makeRepoRelative(resource);
				if (containsPrefix(modified, repoRelativePath))
					return false;
				if (containsPrefix(untracked, repoRelativePath))
					return false;
				if (containsPrefix(missing, repoRelativePath))
					return false;
