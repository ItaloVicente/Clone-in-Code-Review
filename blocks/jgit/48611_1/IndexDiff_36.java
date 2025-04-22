				if (treeIterator != null) {
					if (dirCacheIterator != null) {
						if (!treeIterator.idEqual(dirCacheIterator)
								|| treeIterator
										.getEntryRawMode() != dirCacheIterator
												.getEntryRawMode()) {
							if (!isEntryGitLink(treeIterator)
									|| !isEntryGitLink(dirCacheIterator)
									|| ignoreSubmoduleMode != IgnoreSubmoduleMode.ALL)
								changed.add(treeWalk.getPathString());
						}
					} else {
