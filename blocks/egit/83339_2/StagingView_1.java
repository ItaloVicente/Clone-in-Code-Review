						add((StagingEntry) selected, files);
					} else if (selected instanceof StagingFolderEntry) {
						StagingFolderEntry folder = (StagingFolderEntry) selected;
						for (StagingEntry entry : contentProvider
								.getStagingEntriesFiltered(folder)) {
							add(entry, files);
						}
