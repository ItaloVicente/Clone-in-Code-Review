		addFilteredDescendants(folder, getFilterPattern(), stagingEntries);
		return stagingEntries;
	}

	private void addFilteredDescendants(StagingFolderEntry folder,
			Pattern pattern, List<StagingEntry> result) {
		for (Object child : folder.getChildren()) {
			if (child instanceof StagingFolderEntry) {
				addFilteredDescendants((StagingFolderEntry) child, pattern,
						result);
			} else if (matches((StagingEntry) child, pattern)) {
				result.add((StagingEntry) child);
