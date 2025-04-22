				boolean folderSelected = false;
				for (Object element : selection.toArray()) {
					if (element instanceof StagingFolderEntry) {
						StagingFolderEntry folder = (StagingFolderEntry) element;
						folderSelected = true;
						StagingViewContentProvider contentProvider = getContentProvider(treeViewer);
						List<StagingEntry> stagingEntries = contentProvider
								.getStagingEntriesFiltered(folder);
						for (StagingEntry stagingEntry : stagingEntries) {
							if (!stagingEntryList.contains(stagingEntry))
								stagingEntryList.add(stagingEntry);
						}
					} else if (element instanceof StagingEntry) {
						StagingEntry entry = (StagingEntry) element;
						if (entry.isSubmodule())
							submoduleSelected = true;
						if (!stagingEntryList.contains(entry))
							stagingEntryList.add(entry);
