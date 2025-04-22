			if (dirCacheIterator != null) {
				final DirCacheEntry dirCacheEntry = dirCacheIterator
						.getDirCacheEntry();
				if (dirCacheEntry != null && dirCacheEntry.getStage() > 0) {
					conflicts.add(treeWalk.getPathString());
					continue;
				}
			}

