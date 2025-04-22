			Object selectedItem = iterator.next();
			if (selectedItem instanceof StagingEntry) {
				StagingEntry entry = (StagingEntry) selectedItem;
				selectEntryForUnstaging(headRev, edit, entry);
			} else if (selectedItem instanceof StagingFolderEntry) {
				if (presentation == PRESENTATION_COMPRESSED_FOLDERS) {
					StagingViewContentProvider contentProvider = (StagingViewContentProvider) stagedViewer
							.getContentProvider();
					StagingFolderEntry folder = (StagingFolderEntry) selectedItem;
					StagingEntry[] entries = contentProvider
							.getChildResources(folder);
					for (StagingEntry entry : entries) {
						if (isUnfiltered(entry))
							selectEntryForUnstaging(headRev, edit, entry);
					}
				} else {
					StagingFolderEntry folderEntry = (StagingFolderEntry) selectedItem;
					String filter = null;
					if (filterText != null)
						filter = filterText.getText().trim();
					StagingEntry[] droppedEntries = ((StagingViewContentProvider) stagedViewer
							.getContentProvider()).getStagingEntries(
							folderEntry, filter);
					updateDirCache(new StructuredSelection(droppedEntries),
							headRev, edit);
