
					if (!useIndex)
						rightVersionMap.put(currentPath, GitFileRevision
								.inCommit(repository, rightCommit,
										rightVersionIterator
												.getEntryPathString(), tw
												.getObjectId(rightTreeIndex)));
					else
						rightVersionMap.put(currentPath, GitFileRevision
								.inIndex(repository, rightVersionIterator
										.getEntryPathString()));

