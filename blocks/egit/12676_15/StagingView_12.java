				boolean folderSelected = false;
				for (Object item : selection.toArray()) {
					if (item instanceof StagingFolderEntry) {
						folderSelected = true;
						StagingEntry[] stagingEntries;
						if (presentation == PRESENTATION_COMPRESSED_FOLDERS)
							stagingEntries = ((StagingViewContentProvider) treeViewer
									.getContentProvider())
									.getChildResources((StagingFolderEntry) item);
						else
							stagingEntries = ((StagingViewContentProvider) treeViewer
									.getContentProvider()).getStagingEntries(
									(StagingFolderEntry) item,
									filterText.getText());
						for (StagingEntry stagingEntry : stagingEntries) {
							if (!stagingEntryList.contains(stagingEntry)
									&& isUnfiltered(stagingEntry))
								stagingEntryList.add(stagingEntry);
						}
					} else {
						if (((StagingEntry) item).isSubmodule())
							submoduleSelected = true;
						if (!stagingEntryList.contains(item))
							stagingEntryList.add((StagingEntry) item);
