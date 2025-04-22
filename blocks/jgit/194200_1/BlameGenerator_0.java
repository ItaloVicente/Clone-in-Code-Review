		List<DiffEntry> entries = DiffEntry.scan(treeWalk);

		int heuristicThreshold = 100;
		if (entries.size() > heuristicThreshold) {
			List<DiffEntry> prunedEntries = FindDiffs
					.sameFileNameOrFolderDiffs(path
			if (!prunedEntries.isEmpty()) {
				DiffEntry renameDiff = findRenameDiff(prunedEntries
				if (renameDiff != null) {
					return renameDiff;
				}
			}
		}

		DiffEntry renameDiff = findRenameDiff(entries
		return renameDiff;
	}

	private DiffEntry findRenameDiff(List<DiffEntry> entries
			throws IOException {
