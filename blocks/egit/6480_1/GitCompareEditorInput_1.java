				GitFileRevision baseRev = null;
				if (baseVersionIterator != null) {
					String entryPath = baseVersionIterator.getEntryPathString();
					if (encoding == null) {
						encoding = CompareCoreUtils.getResourceEncoding(repository, entryPath);
					}
					baseRev = GitFileRevision.inCommit(repository, baseCommit,
							entryPath, tw.getObjectId(baseTreeIndex));
				}
