	StagingEntry[] getStagingEntries(StagingFolderEntry folder,
			String filterText) {
		List<StagingEntry> stagingEntries = new ArrayList<StagingEntry>();
		for (StagingEntry stagingEntry : content) {
			if (stagingEntry.getLocation().toString()
					.startsWith(folder.getPath() + "/")) { //$NON-NLS-1$
				if (filterText == null
						|| filterText.length() == 0
						|| stagingEntry.getPath().toUpperCase()
								.contains(filterText.toUpperCase()))
					stagingEntries.add(stagingEntry);
			}
		}
		StagingEntry[] stagingEntryArray = new StagingEntry[stagingEntries
				.size()];
		stagingEntries.toArray(stagingEntryArray);
		return stagingEntryArray;
