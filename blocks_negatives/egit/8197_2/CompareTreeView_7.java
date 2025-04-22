					if (equalContent)
						equalContentPaths.add(currentPath);

					if (equalContent && !showEquals)
						continue;

					while (currentPath.segmentCount() > 0) {
						currentPath = currentPath.removeLastSegments(1);
						if (!baseVersionPathsWithChildren.add(currentPath))
							break;
					}

				} else if (baseVersionIterator != null
						&& compareVersionIterator == null) {
					monitor.setTaskName(baseVersionIterator
							.getEntryPathString());
					IPath currentPath = new Path(baseVersionIterator
							.getEntryPathString());
					addedPaths.add(currentPath);
					if (baseCommit != null)
						baseVersionMap.put(currentPath, GitFileRevision
								.inCommit(repository, baseCommit,
										baseVersionIterator
												.getEntryPathString(), tw
												.getObjectId(baseTreeIndex)));
					while (currentPath.segmentCount() > 0) {
						currentPath = currentPath.removeLastSegments(1);
						if (!baseVersionPathsWithChildren.add(currentPath))
							break;
					}
