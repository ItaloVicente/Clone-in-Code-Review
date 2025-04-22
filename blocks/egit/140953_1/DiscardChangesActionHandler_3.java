	private boolean haveChanges(Map<Repository, Collection<String>> paths) {
		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		for (Map.Entry<Repository, Collection<String>> entry : paths
				.entrySet()) {
			Repository repo = entry.getKey();
			Assert.isNotNull(repo);
			IndexDiffCacheEntry indexDiff = cache.getIndexDiffCacheEntry(repo);
			if (indexDiff == null) {
				return true; // No info, assume worst case
			}
			IndexDiffData diff = indexDiff.getIndexDiff();
			if (diff == null || hasChanges(diff, entry.getValue())) {
				return true;
			}
		}
		return false;
	}

	private boolean hasChanges(@NonNull IndexDiffData diff,
			Collection<String> paths) {
		Set<String> repoPaths = new HashSet<>(paths);
		int n = repoPaths.size();
		repoPaths.removeAll(diff.getAdded());
		if (repoPaths.size() != n) {
			return true;
		}
		repoPaths.removeAll(diff.getChanged());
		if (repoPaths.size() != n) {
			return true;
		}
		repoPaths.removeAll(diff.getModified());
		if (repoPaths.size() != n) {
			return true;
		}
		repoPaths.removeAll(diff.getRemoved());
		return repoPaths.size() != n;
	}

