			} else if (selectedItem instanceof StagingFolderEntry) {
				StagingFolderEntry folderEntry = (StagingFolderEntry) selectedItem;
				String filter = null;
				if (filterText != null)
					filter = filterText.getText().trim();
				StagingEntry[] droppedEntries = ((StagingViewContentProvider) stagedViewer
						.getContentProvider()).getStagingEntries(folderEntry,
						filter);
				updateDirCache(new StructuredSelection(droppedEntries),
						headRev, edit);
