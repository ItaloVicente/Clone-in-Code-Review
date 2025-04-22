						compareVersionMap.put(currentPath, GitFileRevision
								.inIndex(repository, baseVersionIterator
										.getEntryPathString()));
					if (baseCommit != null)
						baseVersionMap.put(currentPath, GitFileRevision
								.inCommit(repository, baseCommit,
										baseVersionIterator
												.getEntryPathString(), tw
												.getObjectId(baseTreeIndex)));
