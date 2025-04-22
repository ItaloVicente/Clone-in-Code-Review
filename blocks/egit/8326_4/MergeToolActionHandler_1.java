
		IndexDiffCache cache = org.eclipse.egit.core.Activator.getDefault().getIndexDiffCache();
		if (cache == null)
			return false;

		IndexDiffCacheEntry entry = cache.getIndexDiffCacheEntry(repo);
		if (entry == null || entry.getIndexDiff() == null)
			return false;

		Set<String> conflictingFiles = entry.getIndexDiff().getConflicting();
		if (conflictingFiles.isEmpty())
			return false;

		for (String selectedRepoPath : selectedRepoPaths) {
			Path selectedPath = new Path(selectedRepoPath);

			for (String conflictingFile : conflictingFiles)
				if (selectedPath.isPrefixOf(new Path(conflictingFile)))
					return true;
