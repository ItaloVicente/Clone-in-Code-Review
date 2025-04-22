		IndexDiffCache diffCache = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache();
		if (diffCache != null) {
			IndexDiffData diffData = diffCache.getIndexDiffCacheEntry(
					repository).getIndexDiff();
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
			}
