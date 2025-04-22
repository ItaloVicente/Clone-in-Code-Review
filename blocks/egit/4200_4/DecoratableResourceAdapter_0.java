	private void extractResourceProperties() {
		String repoRelativePath = makeRepoRelative(resource);
		IndexDiffData cache = Activator.getDefault().getIndexDiffCache().getIndexDiffCacheEntry(repository).getIndexDiff();

		Set<String> untracked = cache.getUntracked();
		tracked = !untracked.contains(repoRelativePath);

		Set<String> added = cache.getAdded();
		Set<String> removed = cache.getRemoved();
		Set<String> changed = cache.getChanged();
		if (added.contains(repoRelativePath)) // added
			staged = Staged.ADDED;
		else if (removed.contains(repoRelativePath)) // removed
			staged = Staged.REMOVED;
		else if (changed.contains(repoRelativePath)) // changed and added into index
			staged = Staged.MODIFIED;
		else
			staged = Staged.NOT_STAGED;
