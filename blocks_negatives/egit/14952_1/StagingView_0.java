						StagingEntry[] stagingEntries;
						if (presentation == PRESENTATION_COMPRESSED_FOLDERS)
							stagingEntries = ((StagingViewContentProvider) treeViewer
									.getContentProvider())
									.getChildResources((StagingFolderEntry) item);
						else
							stagingEntries = ((StagingViewContentProvider) treeViewer
									.getContentProvider()).getStagingEntries(
