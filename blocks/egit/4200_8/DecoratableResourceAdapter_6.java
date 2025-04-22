	private void extractResourceProperties() {
		String repoRelativePath = makeRepoRelative(resource);

		Set<String> untracked = indexDiffData.getUntracked();
		tracked = !untracked.contains(repoRelativePath);

		Set<String> added = indexDiffData.getAdded();
		Set<String> removed = indexDiffData.getRemoved();
		Set<String> changed = indexDiffData.getChanged();
		if (added.contains(repoRelativePath)) // added
			staged = Staged.ADDED;
		else if (removed.contains(repoRelativePath)) // removed
			staged = Staged.REMOVED;
		else if (changed.contains(repoRelativePath)) // changed and added into index
			staged = Staged.MODIFIED;
		else
			staged = Staged.NOT_STAGED;
