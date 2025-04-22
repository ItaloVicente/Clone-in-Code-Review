			} else {
				final File oursFile = createTempFile(
						"_ours"
				final File theirsFile = createTempFile(
						"_theirs"
				final File baseFile = createTempFile(
						"_base"

				final String filePath = tw.getPathString();
				final MergeDriver driver = findMergeDriver(getRepository()
						filePath

				boolean success = driver.merge(getRepository()
						theirsFile

				final File mergedFile;
				if (!inCore)
					mergedFile = updateWorkTree(getRepository()
							tw.getPathString()
							oursFile);
				else
					mergedFile = oursFile;
				updateIndex(getObjectInserter()
						mergedFile);

				if (oursFile != null)
					FileUtils.delete(oursFile);
				if (theirsFile != null)
					FileUtils.delete(theirsFile);
				if (baseFile != null)
					FileUtils.delete(baseFile);

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
