					children.add(new PathNodeAdapter(new PathNode(currentPath,
							Type.FILE_DELETED)));

					if (!useIndex)
						compareVersionMap
								.put(currentPath, GitFileRevision.inCommit(
										repository, compareCommit,
										compareVersionIterator
												.getEntryPathString(), tw
												.getObjectId(compareTreeIndex)));
					else
						compareVersionMap.put(currentPath, GitFileRevision
								.inIndex(repository, compareVersionIterator
										.getEntryPathString()));
