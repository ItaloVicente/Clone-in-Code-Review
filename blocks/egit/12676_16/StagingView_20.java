		resetPathsToExpand();
		for (Object element : selection.toList()) {
			if (element instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) element;
				selectEntryForUnstaging(headRev, edit, entry);
				addPathAndParentPaths(entry.getParentPath(), pathsToExpandInUnstaged);
			} else if (element instanceof StagingFolderEntry) {
				StagingFolderEntry folder = (StagingFolderEntry) element;
				List<StagingEntry> entries = getContentProvider(stagedViewer)
						.getStagingEntriesFiltered(folder);
				for (StagingEntry entry : entries)
					selectEntryForUnstaging(headRev, edit, entry);
				addExpandedPathsBelowFolder(folder, stagedViewer,
						pathsToExpandInUnstaged);
