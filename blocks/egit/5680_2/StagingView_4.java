	private Collection<String> getStagedFileNames() {
		StagingViewContentProvider stagedContentProvider = getContentProvider(stagedTableViewer);
		StagingEntry[] entries = stagedContentProvider.getStagingEntries();
		List<String> files = new ArrayList<String>();
		for (StagingEntry entry : entries)
			files.add(entry.getPath());
		return files;
	}

