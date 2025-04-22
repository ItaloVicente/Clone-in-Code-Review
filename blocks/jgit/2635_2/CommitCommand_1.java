				if (only != null && !only.isEmpty()) {
					DirCache inCoreIndex = DirCache.newInCore();
					DirCacheBuilder dcBuilder = inCoreIndex.builder();

					TreeWalk treeWalk = new TreeWalk(repo);
					treeWalk.addTree(new DirCacheIterator(index));
					if (headId != null)
						treeWalk.addTree(new RevWalk(repo).parseTree(headId));
					treeWalk.addTree(new DirCacheBuildIterator(dcBuilder));
					treeWalk.setRecursive(true);

					while (treeWalk.next()) {
						final String pathString = treeWalk.getPathString();
						if (only.contains(pathString)) {
							DirCacheIterator dcTree = treeWalk.getTree(0
									DirCacheIterator.class);
							if (dcTree != null) {
								DirCacheEntry dirCacheEntry = dcTree
										.getDirCacheEntry();
								if (dirCacheEntry != null)
									dcBuilder.add(dirCacheEntry);
							}

							only.remove(pathString);
						} else {
							if (headId != null) {
								CanonicalTreeParser hTree = treeWalk.getTree(1
										CanonicalTreeParser.class);
								if (hTree != null) {
									DirCacheEntry dirCacheEntry = new DirCacheEntry(
											pathString);
									dirCacheEntry.setObjectId(hTree
											.getEntryObjectId());
									dirCacheEntry.setFileMode(hTree
											.getEntryFileMode());
									dcBuilder.add(dirCacheEntry);
								}
							}
						}
					}

					if (!only.isEmpty())
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().entryNotFoundByPath
										.iterator().next()));

					dcBuilder.finish();

					index.unlock();
					index = inCoreIndex;
				}

