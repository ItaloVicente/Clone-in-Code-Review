					if (!useIndex)
						rightVersionMap.put(currentPath, GitFileRevision
								.inCommit(repository, rightCommit,
										leftVersionIterator
												.getEntryPathString(), tw
												.getObjectId(rightTreeIndex)));
					else
						rightVersionMap.put(currentPath, GitFileRevision
								.inIndex(repository, leftVersionIterator
										.getEntryPathString()));
