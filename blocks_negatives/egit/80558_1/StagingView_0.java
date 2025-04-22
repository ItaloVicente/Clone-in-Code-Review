						List<StagingEntry> stagingEntries = contentProvider
								.getStagingEntriesFiltered(folder);
						for (StagingEntry stagingEntry : stagingEntries) {
							if (!stagingEntryList.contains(stagingEntry))
								stagingEntryList.add(stagingEntry);
						}
