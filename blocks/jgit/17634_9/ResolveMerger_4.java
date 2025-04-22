				final File mergedFile;
				if (!inCore)
					mergedFile = getWorkTreeFile(getRepository()
							tw.getPathString());
				else
					mergedFile = File.createTempFile("merge_"
				writeToFile(mergedFile

				updateIndex(getObjectInserter()
						mergedFile);

				if (!success) {
					unmergedPaths.add(tw.getPathString());
					if (driver instanceof TextMergeDriver)
						mergeResults
								.put(tw.getPathString()
										((TextMergeDriver) driver)
												.getLowLevelResults());
					else
						mergeResults.put(
								tw.getPathString()
								new MergeResult<RawText>(Collections
										.<RawText> emptyList()));
				}
				modifiedFiles.add(tw.getPathString());
			}
