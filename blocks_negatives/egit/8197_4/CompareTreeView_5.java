						compareVersionMap
								.put(currentPath, GitFileRevision.inCommit(
										repository, compareCommit,
										baseVersionIterator
												.getEntryPathString(), tw
												.getObjectId(compareTreeIndex)));
